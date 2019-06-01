package me.kbrewster.hyperium.features.hud.gui

import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.ButtonListWidget

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDConfigureGUI : HyperiumScreen("HUD Config") {

    override fun init() {
        /*children += ButtonListWidget(MinecraftClient.getInstance(), 200, 800, height / 2 - 400, height / 2 + 400, 25).apply {

        }*/
        button("Adjust Position", width / 2 - 100, height / 2 - 60, 200, 20) {

        }

        button("Back", width / 2 - 70, height - 30, 140, 20) {
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
    }

    override fun render(x: Int, y: Int, partialTicks: Float) {
        super.render(x, y, partialTicks)
    }
}