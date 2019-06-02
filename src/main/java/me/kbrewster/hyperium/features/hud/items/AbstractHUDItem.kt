package me.kbrewster.hyperium.features.hud.items

import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import java.awt.Dimension

/**
 * @author Cubxity
 * @since 6/1/2019
 */
abstract class AbstractHUDItem(val name: String) : DrawableHelper() {
    companion object {
        val inGameHud = MinecraftClient.getInstance().inGameHud
    }

    abstract fun render(pos: Position, alignment: Alignment, config: AbstractConfig)

    abstract fun getBounds(config: AbstractConfig): Dimension

    /**
     * This function should return initial state of the config
     */
    abstract fun initConfig(): AbstractConfig

    abstract class AbstractConfig(
            var position: Position = 10 to 10,
            var alignment: Alignment = Alignment.LEFT,
            var color: Int = 0xFFFFFF,
            var opacity: Double = 1.0,
            var scale: Double = 1.0,
            var background: Boolean = false
    )
}