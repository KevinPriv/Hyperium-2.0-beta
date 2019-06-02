package me.kbrewster.hyperium.features.hud.gui

import me.kbrewster.hyperium.features.hud.HUD
import me.kbrewster.hyperium.features.hud.SavedItem
import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDConfigureGUI(val item: SavedItem) : HyperiumScreen("HUD Config") {

    override fun init() {
        button("Delete", width / 2 - 70, height - 50, 140, 20) {
            HUD.config.items.remove(item)
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
        button("Back", width / 2 - 70, height - 30, 140, 20) {
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
    }

    override fun render(x: Int, y: Int, partialTicks: Float) {
        super.render(x, y, partialTicks)
    }
}