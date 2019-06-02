package me.kbrewster.hyperium.features.hud.gui

import com.mojang.blaze3d.platform.GlStateManager
import me.kbrewster.hyperium.features.hud.HUD
import me.kbrewster.hyperium.features.hud.SavedItem
import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDGui : HyperiumScreen("HUD Config") {
    private var selectedItem: SavedItem? = null
    var diffX = 0
    var diffY = 0

    override fun init() {
        button("Add", width / 2 - 70, height - 30, 140, 20) {
            MinecraftClient.getInstance().openScreen(HUDConfigureGUI(SavedItem("hyperium.fps",
                    HUD.hudItems["hyperium.fps"]?.initConfig() ?: return@button)))
        }
    }

    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.render(mouseX, mouseY, partialTicks)
        val i = selectedItem
        if (i != null) {
            val hi = HUD.hudItems[i.id]
            if (hi != null)
                with(i.config) {
                    val bounds = hi.getBounds(this)
                    GlStateManager.scaled(0.5, 0.5, 1.0)
                    val rgb = HUD.rgb
                    fill(position.first * 2, position.second * 2 - 1, (position.first + bounds.width) * 2, position.second * 2 - 2, rgb) // Top line
                    fill(position.first * 2, (position.second + bounds.height) * 2 + 1, (position.first + bounds.width) * 2, (position.second + bounds.height) * 2 + 2, rgb) // Bottom line

                    fill(position.first * 2 - 2, position.second * 2, position.first * 2 - 1, (position.second + bounds.height) * 2, rgb) // Left line
                    fill((position.first + bounds.width) * 2 + 1, position.second * 2, (position.first + bounds.width) * 2 + 2, (position.second + bounds.height) * 2, rgb) // Right line
                    GlStateManager.scaled(1.0, 1.0, 1.0)
                }
        }
//        if (drag)
//            drag = false
    }

    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        super.mouseClicked(x, y, button)
        val i = HUD.config.items.find {
            val bounds = HUD.hudItems[it.id]?.getBounds(it.config)
            val pos = it.config.position
            bounds != null && x.toInt() in pos.first until pos.first + bounds.width && y.toInt() in pos.second until pos.second + bounds.height
        } ?: return true
        selectedItem = i
        diffX = x.toInt() - i.config.position.first
        diffY = y.toInt() - i.config.position.second
        return true
    }

    override fun mouseDragged(x1: Double, y1: Double, button: Int, x2: Double, y2: Double): Boolean {
        super.mouseDragged(x1, y1, button, x2, y2)
        val i = selectedItem ?: return true
        val bounds = HUD.hudItems[i.id]?.getBounds(i.config)
        val pos = i.config.position
        if (bounds != null && x1.toInt() in pos.first until pos.first + bounds.width && y1.toInt() in pos.second until pos.second + bounds.height) {
            i.config.position = x1.toInt() - diffX to y1.toInt() - diffY
        }
        return true
    }
}