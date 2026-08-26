package com.trbear.tanipertiwi.enumerates

enum class PlantAttribute(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    other("other", "Lainnya"),
    grown_on_large_scale("grown on large scale", "Ditanam Skala Besar"),
    grown_on_small_scale("grown on small scale", "Ditanam Skala Kecil"),
    harvested_from_wild("harvested from wild", "Dipanen dari Alam"),
    previously_widely_grown("previously widely grown", "Sebelumnya Ditanam Luas"),
    ;

    companion object {
        val optimal: String = E.Plant_attributes
    }
}