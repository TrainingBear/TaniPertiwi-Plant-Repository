package com.trbear.tanipertiwi.enumerates

enum class Salinitas(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    none("none", "Tidak Ada"),
    low("low", "Rendah"),
    moderate("moderate", "Sedang"),
    high("high", "Tinggi");

    companion object {
        val absolute: String = E.A_soil_salinity
        val optimal: String = E.O_soil_salinity
    }
}