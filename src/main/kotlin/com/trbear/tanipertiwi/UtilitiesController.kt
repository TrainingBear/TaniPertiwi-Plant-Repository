package com.trbear.tanipertiwi

import com.trbear.tanipertiwi.enumerates.Bentuk
import com.trbear.tanipertiwi.enumerates.Category
import com.trbear.tanipertiwi.enumerates.Climate
import com.trbear.tanipertiwi.enumerates.Depth
import com.trbear.tanipertiwi.enumerates.Drainase
import com.trbear.tanipertiwi.enumerates.Fotosintetis
import com.trbear.tanipertiwi.enumerates.Habit
import com.trbear.tanipertiwi.enumerates.Kesuburan
import com.trbear.tanipertiwi.enumerates.Lifespan
import com.trbear.tanipertiwi.enumerates.Pencahayaan
import com.trbear.tanipertiwi.enumerates.PlantAttribute
import com.trbear.tanipertiwi.enumerates.Salinitas
import com.trbear.tanipertiwi.enumerates.Texture
import com.trbear.tanipertiwi.enumerates.TranslatableEnum
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UtilitiesController {
    @GetMapping("/api/id/complete/categories")
    fun getIdCategories(): List<String> = translations(Category.entries)

    @GetMapping("/api/origin/complete/categories")
    fun getOriginCategories(): List<String> = origins(Category.entries)

    @GetMapping("/api/id/complete/climates")
    fun getIdClimates(): List<String> = translations(Climate.entries)

    @GetMapping("/api/origin/complete/climates")
    fun getOriginClimates(): List<String> = origins(Climate.entries)

    @GetMapping("/api/id/complete/depths")
    fun getIdDepths(): List<String> = translations(Depth.entries)

    @GetMapping("/api/origin/complete/depths")
    fun getOriginDepths(): List<String> = origins(Depth.entries)

    @GetMapping("/api/id/complete/drainases")
    fun getIdDrainases(): List<String> = translations(Drainase.entries)

    @GetMapping("/api/origin/complete/drainases")
    fun getOriginDrainases(): List<String> = origins(Drainase.entries)

    @GetMapping("/api/id/complete/bentuks")
    fun getIdBentuks(): List<String> = translations(Bentuk.entries)

    @GetMapping("/api/origin/complete/bentuks")
    fun getOriginBentuks(): List<String> = origins(Bentuk.entries)

    @GetMapping("/api/id/complete/fotosintetises")
    fun getIdFotosintetises(): List<String> = translations(Fotosintetis.entries)

    @GetMapping("/api/origin/complete/fotosintetises")
    fun getOriginFotosintetises(): List<String> = origins(Fotosintetis.entries)

    @GetMapping("/api/id/complete/habits")
    fun getIdHabits(): List<String> = translations(Habit.entries)

    @GetMapping("/api/origin/complete/habits")
    fun getOriginHabits(): List<String> = origins(Habit.entries)

    @GetMapping("/api/id/complete/kesuburans")
    fun getIdKesuburans(): List<String> = translations(Kesuburan.entries)

    @GetMapping("/api/origin/complete/kesuburans")
    fun getOriginKesuburans(): List<String> = origins(Kesuburan.entries)

    @GetMapping("/api/id/complete/lifespans")
    fun getIdLifespans(): List<String> = translations(Lifespan.entries)

    @GetMapping("/api/origin/complete/lifespans")
    fun getOriginLifespans(): List<String> = origins(Lifespan.entries)

    @GetMapping("/api/id/complete/pencahayaans")
    fun getIdPencahayaans(): List<String> = translations(Pencahayaan.entries)

    @GetMapping("/api/origin/complete/pencahayaans")
    fun getOriginPencahayaans(): List<String> = origins(Pencahayaan.entries)

    @GetMapping("/api/id/complete/plantattributes")
    fun getIdPlantattributes(): List<String> = translations(PlantAttribute.entries)

    @GetMapping("/api/origin/complete/plantattributes")
    fun getOriginPlantattributes(): List<String> = origins(PlantAttribute.entries)

    @GetMapping("/api/id/complete/salinitases")
    fun getIdSalinitases(): List<String> = translations(Salinitas.entries)

    @GetMapping("/api/origin/complete/salinitases")
    fun getOriginSalinitases(): List<String> = origins(Salinitas.entries)

    @GetMapping("/api/id/complete/textures")
    fun getIdTextures(): List<String> = translations(Texture.entries)

    @GetMapping("/api/origin/complete/textures")
    fun getOriginTextures(): List<String> = origins(Texture.entries)

    private fun translations(entries: Iterable<TranslatableEnum>): List<String> =
        entries.map { it.translation }

    private fun origins(entries: Iterable<TranslatableEnum>): List<String> =
        entries.map { it.origin }
}
