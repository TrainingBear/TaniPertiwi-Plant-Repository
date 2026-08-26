package com.trbear.tanipertiwi.enumerates

enum class Pencahayaan(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    heavy_shade("heavy shade", "Naungan Berat"),
    light_shade("light shade", "Naungan Ringan"),
    cloudy_skies("cloudy skies", "Langit Berawan"),
    clear_skies("clear skies", "Langit Cerah"),
    very_bright("very bright", "Sangat Terang");

    companion object {
        val optimal_min: String = E.O_minimum_light_intentsity
        val optimal_max: String = E.O_maximum_light_intentsity
        val absolute_min: String = E.A_minimum_light_intensity
        val absolute_max: String = E.A_maximum_light_intensity
    }
}