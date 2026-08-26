package com.trbear.tanipertiwi.enumerates

enum class Habit(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    erect("erect", "Tegak"),
    prostrate_procumbent_semierect("prostrate/procumbent/semi-erect", "Menjalar/Setengah Tegak"),
    climber_scrambler_scadent("climber/scrambler/scadent", "Merambat"),
    acaulescent_or_rosette_plants("acaulescent(or rosette plants)", "Tanpa Batang (Roset)"),
    ;

    companion object {
        val optimal: String = E.Habitat
    }
}