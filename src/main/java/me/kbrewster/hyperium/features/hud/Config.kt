package me.kbrewster.hyperium.features.hud

import me.kbrewster.hyperium.features.hud.items.AbstractHUDItem
import me.kbrewster.hyperium.features.hud.items.FPSHUDItem

data class Config(
        val items: MutableList<SavedItem> = mutableListOf(SavedItem("hyperium.fps", FPSHUDItem.Config()))
)

/**
 * @param id hud item id
 */
data class SavedItem(val id: String, val config: AbstractHUDItem.AbstractConfig)

enum class Alignment {
    LEFT
}