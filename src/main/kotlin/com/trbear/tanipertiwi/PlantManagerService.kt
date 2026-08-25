package com.trbear.tanipertiwi

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@Service
class PlantManagerService(private val datasetService: DatasetService) {
    private val mapper: ObjectMapper = jacksonObjectMapper()
    private val plantsFile = "plants.json"
    private val ecoCropFile = "EcoCrop_DB.csv"

    fun list(query: String?): List<Map<String, String?>> {
        val term = query?.trim()?.lowercase().orEmpty()
        return plants().map { plant -> summary(plant) }
            .filter { term.isBlank() || it.values.any { value -> value?.lowercase()?.contains(term) == true } }
            .sortedBy { it["scientificName"]?.lowercase() }
    }

    fun detail(scientificName: String): ObjectNode {
        val plant = findPlant(scientificName)
        val result = mapper.createObjectNode()
        result.set<ObjectNode>("json", plant)
        result.set<ObjectNode>("ecocrop", findEcoCrop(scientificName) ?: emptyEcoCrop())
        return result
    }

    fun template(): ObjectNode = mapper.createObjectNode().also { result ->
        result.set<ObjectNode>("json", mapper.createObjectNode().also { plant ->
            plant.put("difficulty", "MEDIUM")
            plant.set<ObjectNode>("plant_care", mapper.createObjectNode())
            plant.set<ObjectNode>("product_system", mapper.createObjectNode())
        })
        result.set<ObjectNode>("ecocrop", emptyEcoCrop())
    }

    fun create(payload: JsonNode): ObjectNode = save(payload, null)

    fun update(scientificName: String, payload: JsonNode): ObjectNode = save(payload, scientificName)

    fun delete(scientificName: String) {
        val plantEntries = plants()
        val plant = findPlant(scientificName, plantEntries)
        val ecoRows = ecoCrops()
        val remainingPlants = mapper.createArrayNode().also { output ->
            plantEntries.filter { it !== plant }.forEach(output::add)
        }
        val remainingEco = ecoRows.filter { it["ScientificName"]?.asText() != scientificName }
        writeBoth(remainingPlants, remainingEco)
    }

    fun uploadImage(fileName: String, bytes: ByteArray): String {
        val safeName = Path.of(fileName).fileName.toString().replace(Regex("[^A-Za-z0-9._ -]"), "_")
        if (safeName.isBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "An image filename is required")
        val directory = datasetService.resolve("images")
        Files.createDirectories(directory)
        Files.write(directory.resolve(safeName), bytes)
        return "/api/images/$safeName"
    }

    private fun save(payload: JsonNode, originalName: String?): ObjectNode {
        val json = payload.path("json") as? ObjectNode
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "json must be an object")
        val eco = payload.path("ecocrop") as? ObjectNode
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "ecocrop must be an object")
        val scientificName = json.path("nama_ilmiah").asText().trim()
        if (scientificName.isBlank()) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Scientific name is required"
        )

        json.put("nama_ilmiah", scientificName)
        eco.put("ScientificName", scientificName)
        val currentPlants = plants()
        val oldPlant = originalName?.let { findPlant(it, currentPlants) }
        if (currentPlants.any { it !== oldPlant && it.path("nama_ilmiah").asText() == scientificName }) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "A plant with this scientific name already exists")
        }
        val updatedPlants = mapper.createArrayNode().also { output ->
            currentPlants.forEach { output.add(if (it === oldPlant) json else it) }
            if (oldPlant == null) output.add(json)
        }
        val currentEco = ecoCrops()
        val updatedEco = currentEco.toMutableList()
        val oldIndex =
            originalName?.let { name -> updatedEco.indexOfFirst { it["ScientificName"]?.asText() == name } } ?: -1
        if (oldIndex >= 0) updatedEco[oldIndex] = eco
        else if (updatedEco.none { it["ScientificName"]?.asText() == scientificName }) updatedEco.add(eco)
        else throw ResponseStatusException(HttpStatus.CONFLICT, "EcoCrop already has this scientific name")
        writeBoth(updatedPlants, updatedEco)
        return detail(scientificName)
    }

    private fun plants(): List<ObjectNode> {
        val root = mapper.readTree(datasetService.resolve(plantsFile).toFile()) as? ArrayNode
            ?: throw IllegalStateException("plants.json must contain an array")
        return root.map { it.deepCopy<ObjectNode>() }
    }

    private fun ecoCrops(): List<ObjectNode> {
        val path = datasetService.resolve(ecoCropFile)
        Files.newBufferedReader(path, StandardCharsets.UTF_8).use { reader ->
            CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader).use { parser ->
                return parser.map { record ->
                    mapper.createObjectNode()
                        .also { node -> parser.headerNames.forEach { node.put(it, record.get(it)) } }
                }
            }
        }
    }

    private fun headers(): List<String> =
        Files.newBufferedReader(datasetService.resolve(ecoCropFile), StandardCharsets.UTF_8).use { reader ->
            CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)
                .use { it.headerNames }
        }

    private fun writeBoth(plantData: ArrayNode, ecoData: List<ObjectNode>) {
        val jsonPath = datasetService.resolve(plantsFile)
        val csvPath = datasetService.resolve(ecoCropFile)
        val jsonTemp = Files.createTempFile(jsonPath.parent, "plants-", ".json")
        val csvTemp = Files.createTempFile(csvPath.parent, "ecocrop-", ".csv")
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonTemp.toFile(), plantData)
            val headerNames = headers()
            Files.newBufferedWriter(csvTemp, StandardCharsets.UTF_8).use { writer ->
                CSVPrinter(
                    writer,
                    CSVFormat.DEFAULT.builder().setHeader(*headerNames.toTypedArray()).build()
                ).use { printer ->
                    ecoData.forEach { row -> printer.printRecord(headerNames.map { row.path(it).asText("") }) }
                }
            }
            Files.move(jsonTemp, jsonPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
            Files.move(csvTemp, csvPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        } finally {
            Files.deleteIfExists(jsonTemp)
            Files.deleteIfExists(csvTemp)
        }
    }

    private fun findPlant(scientificName: String, source: List<ObjectNode> = plants()): ObjectNode =
        source.firstOrNull { it.path("nama_ilmiah").asText() == scientificName }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found")

    private fun findEcoCrop(scientificName: String): ObjectNode? = ecoCrops()
        .firstOrNull { it.path("ScientificName").asText() == scientificName }

    private fun emptyEcoCrop(): ObjectNode =
        mapper.createObjectNode().also { row -> headers().forEach { row.put(it, "") } }

    private fun summary(plant: ObjectNode) = mapOf(
        "scientificName" to plant.path("nama_ilmiah").asText(),
        "commonName" to plant.path("common_name").asText(null),
        "family" to plant.path("family").asText(null),
        "difficulty" to plant.path("difficulty").asText(null),
        "image" to plant.path("thumbnail").asText(null)
    )
}
