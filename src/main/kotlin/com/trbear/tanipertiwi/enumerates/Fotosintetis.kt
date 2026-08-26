package com.trbear.tanipertiwi.enumerates

enum class Fotosintetis(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    short_day("short day", "Hari Pendek"),
    long_day("long day", "Hari Panjang"),
    neutral_day("neutral day", "Hari Netral"),
    sensitive("sensitive", "Sensitif");

    companion object {
        val optimal: String = E.Photoperiod
    }
}