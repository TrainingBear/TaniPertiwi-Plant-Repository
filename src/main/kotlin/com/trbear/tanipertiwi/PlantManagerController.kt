package com.trbear.tanipertiwi

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
class PlantManagerController(private val plants: PlantManagerService) {
    @GetMapping("/") fun index() = "plant-manager"

    @GetMapping("/api/plants") @ResponseBody
    fun list(@RequestParam(required = false) query: String?) = plants.list(query)

    @GetMapping("/api/plants/{scientificName}") @ResponseBody
    fun detail(@PathVariable scientificName: String) = plants.detail(scientificName)

    @GetMapping("/api/plants/template") @ResponseBody
    fun template() = plants.template()

    @PostMapping("/api/plants") @ResponseBody
    fun create(@RequestBody payload: JsonNode): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(plants.create(payload))
    }

    @PutMapping("/api/plants/{scientificName}") @ResponseBody
    fun update(@PathVariable scientificName: String, @RequestBody payload: JsonNode): Map<String, Any> {
        return plants.update(scientificName, payload)
    }

    @DeleteMapping("/api/plants/{scientificName}") @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable scientificName: String) {
        return plants.delete(scientificName)
    }

    @PostMapping("/api/images", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]) @ResponseBody
    fun upload(@RequestParam file: MultipartFile): Map<String, String> {
        if (file.isEmpty) throw IllegalArgumentException("Choose an image first")
        return mapOf("path" to plants.uploadImage(file.originalFilename ?: "image", file.bytes))
    }

    @GetMapping("/api/images/{filename:.+}")
    fun image(@PathVariable filename: String): ResponseEntity<FileSystemResource> {
        val file = java.nio.file.Path.of("data", "images", filename).toAbsolutePath().normalize()
        if (!java.nio.file.Files.isRegularFile(file)) return ResponseEntity.notFound().build<FileSystemResource>()
        val type = java.nio.file.Files.probeContentType(file) ?: MediaType.APPLICATION_OCTET_STREAM_VALUE
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(type)).body(FileSystemResource(file))
    }
}
