package com.trbear.tanipertiwi.enumerates

enum class Category(
    override val origin: String,
    override val translation: String
): TranslatableEnum {
    OTHER("other", "Lainnya"),
    CEREALS_PSEUDOCEREALS("cereals & pseudocereals", "Pseudocereal"),
    PULLS("pulses (grain legumes)", "Kacang-Kacangan"),
    ROOTS_TUBERS("roots/tubers", "Akar/Umbi"),
    FORAGE_PASTURES("forage/pasture", "Pakan ternak"),
    FRUIT_NUT("fruits & nuts", "Buah & kacang"),
    VEGETABLES("vegetables", "Sayur"),
    MATERIALS("materials", "Bahan"),
    ORNAMENTALS_TURF("ornamentals/turf", "Rumput hias"),
    MEDICINALS_AND_ARMOATIC("medicinals & aromatic", "Obat & aromatik"),
    FOREST_OR_WOOD("forest/wood", "Hutan/Kayu"),
    COVER_CROP("cover crop", "Tanaman Penutup Tanah"),
    ENVIRONMENTAL("environmental", "Lingkungan"),
    WEED("weed", "Gulma");
}

enum class Climate(
    override val origin: String,
    override val translation: String
): TranslatableEnum {
    TROPICAL_WET_AND_DRY(
        "tropical wet & dry (Aw)",
        "tropis basah & kering (Aw)"
    ),

    TROPICAL_WET(
        "tropical wet (Ar)",
        "tropis kering"
    ),

    DESERT_OR_ARID(
        "desert or arid (Bw)",
        "gurun"
    ),

    STEPPE_OR_SEMIARID(
        "steppe or semiarid (Bs)",
        "Stepa/Semi-kering"
    ),

    SUBTROPICAL_HUMID(
        "subtropical humid (Cf)",
        "subtropis basah"
    ),

    SUBTROPICAL_DRY_SUMMER(
        "subtropical dry summer (Cs)",
        "mediterania"
    ),

    SUBTROPICAL_DRY_WINTER(
        "subtropical dry winter (Cw)",
        "subtropis lembab"
    ),

    TEMPERATE_OCEANIC(
        "temperate oceanic (Do)",
        "maritim"
    ),

    TEMPERATE_CONTINENTAL(
        "temperate continental (Dc)",
        "Kontinental Sedang"
    ),

    TEMPERATE_WITH_HUMID_WINTERS(
        "temperate with humid winters (Df)",
        "Sedang dengan Musim Dingin Lembab"
    ),

    TEMPERATE_WITH_DRY_WINTERS(
        "temperate with dry winters (Dw)",
        "dingin"
    ),

    BOREAL(
        "boreal (E)",
        "taiga"
    ),

    POLAR(
        "polar (F)",
        "kutub"
    );

    companion object {
        const val optimal = "Climate_zone"

        fun fromHead(head: String): Climate? =
            entries.find { it.origin == head }
    }
}