package me.kbrewster.hyperium.bin.hud.items

import com.mojang.blaze3d.platform.GlStateManager
import me.kbrewster.hyperium.bin.hud.Alignment
import me.kbrewster.hyperium.utils.Position
import java.awt.Color
import java.awt.Dimension

abstract class TextHudItem(name: String, val initialFormat: String) : AbstractHUDItem(name) {

    val height = inGameHud.fontRenderer.fontHeight

    override fun render(pos: Position, alignment: Alignment, config: AbstractConfig) {
        GlStateManager.disableBlend()
        GlStateManager.alphaFunc(516, 1 - config.opacity.toFloat())
        GlStateManager.scaled(config.scale, config.scale, 1.0)
        config as Config
        val s = format(config.format)
        if (config.background)
            fill(pos.first - 1, pos.second - 1, pos.first + inGameHud.fontRenderer.getStringWidth(s) + 1, pos.second + height + 1, Color(0, 0, 0, (config.backgroundOpacity * 255).toInt()).rgb)
        if (config.shadow)
            inGameHud.fontRenderer.drawWithShadow(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        else
            inGameHud.fontRenderer.draw(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        GlStateManager.scaled(1.0, 1.0, 1.0)
        GlStateManager.alphaFunc(516, 0F)
    }

    override fun getBounds(config: AbstractConfig): Dimension =
            Dimension(inGameHud.fontRenderer.getStringWidth(format((config as Config).format)), height)

    abstract fun format(format: String): String

    override fun initConfig() = Config(initialFormat)

    data class Config(var format: String, var shadow: Boolean = true) : AbstractHUDItem.AbstractConfig()
}