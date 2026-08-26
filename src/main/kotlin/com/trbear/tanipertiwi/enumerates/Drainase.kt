package com.trbear.tanipertiwi.enumerates

enum class Drainase(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    poorly("poorly (saturated >50% of year)", "Buruk (Tergenang >50% setahun)"),
    well("well (dry spells)", "Baik"),
    excessive("excessive (dry/moderately dry)", "Berlebihan (Sangat Cepat)");

    companion object {
        val absolute: String = E.A_soil_drainage
        val optimal: String = E.O_soil_drainage
    }
}