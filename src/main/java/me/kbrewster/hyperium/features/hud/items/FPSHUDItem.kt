package me.kbrewster.hyperium.features.hud.items

import com.mojang.blaze3d.platform.GlStateManager
import me.kbrewster.hyperium.features.hud.Alignment
import me.kbrewster.hyperium.utils.Position
import net.minecraft.client.MinecraftClient
import java.awt.Dimension


/**
 * @author Cubxity
 * @since 6/1/2019
 */

class FPSHUDItem : AbstractHUDItem("FPS") {
    val height = inGameHud.fontRenderer.fontHeight

    override fun render(pos: Position, alignment: Alignment, config: AbstractConfig) {
        GlStateManager.scaled(config.scale, config.scale, 1.0)
        config as Config
        val s = config.format.replace("{FPS}", MinecraftClient.getCurrentFps().toString())
        if (config.background)
            fill(pos.first - 1, pos.second - 1, pos.first + inGameHud.fontRenderer.getStringWidth(s) + 1, pos.second + height + 1, config.color)
        if (config.shadow)
            inGameHud.fontRenderer.drawWithShadow(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        else
            inGameHud.fontRenderer.drawWithShadow(s, pos.first.toFloat(), pos.second.toFloat(), config.color)
        GlStateManager.scaled(1.0, 1.0, 1.0)
    }

    override fun getBounds(config: AbstractConfig) =
            Dimension(inGameHud.fontRenderer.getStringWidth((config as Config).format.replace("{FPS}", MinecraftClient.getCurrentFps().toString())), height)

    data class Config(var format: String = "{FPS} FPS", var shadow: Boolean = true) : AbstractHUDItem.AbstractConfig()
}