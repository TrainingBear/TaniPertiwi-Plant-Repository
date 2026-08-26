package com.trbear.tanipertiwi.enumerates

enum class Lifespan(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    ephemeral("ephemeral", "Efemer"),
    annual("annual", "Semusim (Tahunan)"),
    perennial("perennial", "Menahun"),
    biennial("biennial", "Dua Musim");

    companion object {
        val optimal: String = E.Life_span
    }
}