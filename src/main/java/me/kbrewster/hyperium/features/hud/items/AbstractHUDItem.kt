package me.kbrewster.hyperium.features.hud.items

import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position


/**
 * @author Cubxity
 * @since 6/1/2019
 */
abstract class AbstractHUDItem(val name: String) {
    abstract fun render(pos: Position, alignment: Alignment, config: AbstractConfig)

    abstract class AbstractConfig(var background: Boolean = false)
}