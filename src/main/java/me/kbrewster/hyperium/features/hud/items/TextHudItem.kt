package me.kbrewster.hyperium.features.hud.items

import com.mojang.blaze3d.platform.GlStateManager
import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position
import java.awt.Dimension

abstract class TextHudItem(name: String, val initialFormat: String) : AbstractHUDItem(name) {

    val height = inGameHud.fontRenderer.fontHeight

    override fun render(pos: Position, alignment: Alignment, config: AbstractConfig) {
        GlStateManager.scaled(config.scale, config.scale, 1.0)
        config as Config
        val s = format(config.format)
        if (config.background)
            fill(pos.first - 1, pos.second - 1, pos.first + inGameHud.fontRenderer.getStringWidth(s) + 1, pos.second + height + 1, config.color)
        if (config.shadow)
            inGameHud.fontRenderer.drawWithShadow(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        else
            inGameHud.fontRenderer.drawWithShadow(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        GlStateManager.scaled(1.0, 1.0, 1.0)
    }

    override fun getBounds(config: AbstractConfig): Dimension =
            Dimension(inGameHud.fontRenderer.getStringWidth(format((config as Config).format)), height)

    abstract fun format(format: String): String

    override fun initConfig() = Config(initialFormat)

    data class Config(var format: String, var shadow: Boolean = true) : AbstractHUDItem.AbstractConfig()
}