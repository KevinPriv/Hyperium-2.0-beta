package me.kbrewster.hyperium.features.hud

import me.kbrewster.hyperium.features.hud.items.AbstractHUDItem

data class Config(
        val items: MutableList<SavedItem> = mutableListOf(
                SavedItem("hyperium.fps", HUD.hudItems["hyperium.fps"]!!.initConfig()),
                SavedItem("hyperium.position", HUD.hudItems["hyperium.position"]!!.initConfig())
        )
)

/**
 * @param id hud item id
 */
data class SavedItem(val id: String, val config: AbstractHUDItem.AbstractConfig)

enum class Alignment {
    LEFT
}