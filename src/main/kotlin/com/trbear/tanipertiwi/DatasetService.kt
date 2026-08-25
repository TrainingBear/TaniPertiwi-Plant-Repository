package com.trbear.tanipertiwi

import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

@Service
class DatasetService {
    private val dataDirectory: Path = Path.of("data").toAbsolutePath().normalize()

    init {
        Files.createDirectories(dataDirectory)
    }

    @Throws(IOException::class)
    fun listFiles(): List<String?> {
        try {
            Files.list(dataDirectory).use { files ->
                return files
                    .filter(Files::isRegularFile)
                    .map { path -> path.fileName.toString() }
                    .filter { name ->
                        name.endsWith(".csv") ||
                                name.endsWith(".json")
                    }
                    .sorted()
                    .toList()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun resolve(filename: String?): Path {
        require(!filename.isNullOrBlank()) { "Filename is required" }
        val path = dataDirectory.resolve(filename).normalize()

        require(path.startsWith(dataDirectory.toAbsolutePath().normalize())) { "Invalid filename" }

        return path
    }
}
