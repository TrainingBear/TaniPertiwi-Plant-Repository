package com.trbear.tanipertiwi.enumerates

enum class Bentuk(
    override val origin: String,
    override val translation: String
) : TranslatableEnum {
    ;

    companion object {
        val optimal: String = E.Life_form
    }
}