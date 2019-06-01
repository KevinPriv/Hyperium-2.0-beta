package me.kbrewster.hyperium.features.hud.items

import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position


/**
 * @author Cubxity
 * @since 6/1/2019
 */

class FPSHUDItem : AbstractHUDItem("FPS") {
    override fun render(pos: Position, alignment: Alignment, config: AbstractConfig) {

    }

    data class Config(var format: String = "{FPS} FPS") : AbstractHUDItem.AbstractConfig()
}