package com.trbear.tanipertiwi

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path

@Service
class JsonService(private val datasetService: DatasetService) {
    private val mapper: ObjectMapper = ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)

    @Throws(IOException::class)
    fun read(filename: String?): String {
        val path: Path = datasetService.resolve(filename)

        val json: JsonNode? = mapper.readTree(path.toFile())

        return mapper.writeValueAsString(json)
    }

    @Throws(IOException::class)
    fun save(filename: String?, content: String?) {
        val json: JsonNode? = mapper.readTree(content)

        val path: Path = datasetService.resolve(filename)

        mapper.writeValue(path.toFile(), json)
    }
}