package me.kbrewster.hyperium.features.hud

import me.kbrewster.hyperium.features.hud.items.AbstractHUDItem
import me.kbrewster.hyperium.utils.Position

data class Config(
        val packs: MutableList<SavedPack> = mutableListOf()
)

/**
 * @param position position of the pack
 * @param alignment alignment of the pack
 */
data class SavedPack(val position: Position, val alignment: Alignment, val items: MutableList<SavedItem> = mutableListOf())

/**
 * @param id hud item id
 */
data class SavedItem(val id: String, val config: AbstractHUDItem.AbstractConfig)

enum class Alignment {
    LEFT
}