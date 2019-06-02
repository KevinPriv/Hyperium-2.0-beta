package me.kbrewster.hyperium.features.hud.items

import net.minecraft.client.MinecraftClient

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class PosHUDItem : TextHudItem("Position", "X: {X} Y: {Y} Z: {Z}") {
    override fun format(format: String): String {
        val pos = MinecraftClient.getInstance().player.pos
        return format
                .replace("{X}", pos.x.toInt().toString())
                .replace("{Y}", pos.y.toInt().toString())
                .replace("{Z}", pos.z.toInt().toString())
    }
}