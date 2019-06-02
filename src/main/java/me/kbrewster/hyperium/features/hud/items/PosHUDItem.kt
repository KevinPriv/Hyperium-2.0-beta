package me.kbrewster.hyperium.features.hud.items

import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position
import java.awt.Dimension


/**
 * @author Cubxity
 * @since 6/1/2019
 */
class PosHUDItem : AbstractHUDItem("Position") {
    override fun render(pos: Position, alignment: Alignment, config: AbstractConfig) {

    }

    override fun getBounds(config: AbstractConfig): Dimension {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class Config(var format: String = "X: {X} Y: {Y} Z: {Z}") : AbstractConfig()
}