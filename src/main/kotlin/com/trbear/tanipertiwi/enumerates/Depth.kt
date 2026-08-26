package com.trbear.tanipertiwi.enumerates

enum class Depth(
    override val origin: String,
    override val translation: String,
    val value: Int
): TranslatableEnum {
    VERY_SHALLOW(
        "verry shallow",
        "Sangat dangkal (< 30cm)",
        15
    ),

    SHALLOW(
        "shallow",
        "Dangkal (>= 30cm)",
        30
    ),

    MEDIUM(
        "medium",
        "Sedang (>= 60cm)",
        60
    ),

    DEEP(
        "deep",
        "Dalam (>= 120cm)",
        120
    );

    companion object {
        const val absolute = "A_soil_depth"
        const val optimal = "O_soil_depth"

        fun fromOriginal(value: String): Depth? =
            entries.find { it.origin == value }

        fun scaleFrom(value: Float): Depth {
            return if (value >= 120f) DEEP
            else if (value >= 60f) MEDIUM
            else if (value >= 30f) SHALLOW
            else VERY_SHALLOW
        }
    }
}