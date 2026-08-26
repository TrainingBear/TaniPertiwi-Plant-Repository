package com.trbear.tanipertiwi.enumerates

enum class Texture(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    heavy("heavy", "Berat (Liat)"),
    medium("medium", "Sedang (Lempung)"),
    light("light", "Ringan (Pasir)"),
    wide("wide", "Luas"),
    organic("organic", "Organik");

    companion object {
        val absolute: String = E.A_soil_texture
        val optimal: String = E.O_soil_texture
    }
}