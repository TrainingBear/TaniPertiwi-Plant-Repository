package com.trbear.tanipertiwi

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

@Service
class CsvService(private val datasetService: DatasetService) {
    @Throws(IOException::class)
    fun read(filename: String?): CsvData {
        val path: Path = datasetService.resolve(filename)

        Files.newBufferedReader(path).use { reader ->
            CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader).use { parser ->
                    val headers: List<String?> = parser.headerNames
                    val rows: MutableList<List<String?>?> = ArrayList()

                    for (record in parser) {
                        val row: MutableList<String?> = ArrayList()

                        for (header in headers) {
                            row.add(record.get(header))
                        }

                        rows.add(row)
                    }
                    return CsvData(headers, rows)
                }
        }
    }

    @Throws(IOException::class)
    fun save(filename: String?, data: CsvData) {
        val path: Path = datasetService.resolve(filename)

        Files.newBufferedWriter(path).use { writer ->
            CSVFormat.DEFAULT.builder()
                .setHeader(*(data.headers ?: emptyList()).map { it.orEmpty() }.toTypedArray())
                .build()
                .print(writer).use { printer: CSVPrinter ->
                    for (row in data.rows!!) {
                        printer.printRecord(row)
                    }
                }
        }
    }
}
