package me.kbrewster.hyperium.features.hud.gui

import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDGui : HyperiumScreen("HUD Config") {

    override fun init() {
        button("Add", width / 2 - 70, height - 30, 140, 20) {
            MinecraftClient.getInstance().openScreen(HUDConfigureGUI())
        }
    }

    override fun render(x: Int, y: Int, partialTicks: Float) {
        super.render(x, y, partialTicks)
    }
}