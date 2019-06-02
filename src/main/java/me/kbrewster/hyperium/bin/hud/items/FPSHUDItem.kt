package me.kbrewster.hyperium.bin.hud.items

import net.minecraft.client.MinecraftClient


/**
 * @author Cubxity
 * @since 6/1/2019
 */
class FPSHUDItem : TextHudItem("FPS", "{FPS} FPS") {
    override fun format(format: String) = format.replace("{FPS}", MinecraftClient.getCurrentFps().toString())
}