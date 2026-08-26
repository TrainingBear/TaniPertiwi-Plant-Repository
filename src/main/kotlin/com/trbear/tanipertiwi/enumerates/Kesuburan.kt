package com.trbear.tanipertiwi.enumerates

enum class Kesuburan(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    low("low", "Rendah"),
    moderate("moderate", "Sedang"),
    high("high", "Tinggi");

    companion object {
        val absolute: String = E.A_soil_fertility
        val optimal: String = E.O_soil_fertility
    }
}