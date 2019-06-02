package me.kbrewster.hyperium.bin.hud

import me.kbrewster.hyperium.bin.hud.items.AbstractHUDItem

data class Config(
        val items: MutableList<SavedItem> = mutableListOf(
                SavedItem("hyperium.fps", HUD.hudItems["hyperium.fps"]!!.initConfig()),
                SavedItem("hyperium.position", HUD.hudItems["hyperium.position"]!!.initConfig().apply { position = 10 to 20 })
        )
)

/**
 * @param id hud item id
 */
data class SavedItem(val id: String, val config: AbstractHUDItem.AbstractConfig)

enum class Alignment {
    LEFT
}