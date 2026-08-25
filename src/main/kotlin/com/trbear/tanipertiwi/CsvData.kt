package com.trbear.tanipertiwi

data class CsvData(
    @JvmField val headers: List<String?>?,
    @JvmField val rows: List<List<String?>?>?
)
