package me.kbrewster.hyperium.bin.hud.gui

import com.mojang.blaze3d.platform.GlStateManager
import me.kbrewster.hyperium.bin.hud.HUD
import me.kbrewster.hyperium.bin.hud.SavedItem
import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient
import kotlin.math.absoluteValue

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDGui : HyperiumScreen("HUD Config") {
    private var selectedItem: SavedItem? = null
    var diffX = 0
    var diffY = 0
    var lastClick = 0L

    override fun init() {
        button("Add", width / 2 - 70, height - 30, 140, 20) {
            val i = SavedItem("hyperium.fps", HUD.hudItems["hyperium.fps"]?.initConfig() ?: return@button)
            HUD.config.items += i
            MinecraftClient.getInstance().openScreen(HUDConfigureGUI(i))
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

                    val pos = i.config.position
                    HUD.config.items.forEach {
                        if (it == i) return@forEach
                        val ip = it.config.position
                        if (pos.second == ip.second)
                            fill(pos.first * 2, pos.second * 2, ip.first * 2, pos.second * 2 + 1, rgb)
                        if (pos.first == ip.first)
                            fill(pos.first * 2, pos.second * 2, pos.first * 2 + 1, ip.second * 2, rgb)
                    }

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
        if (System.currentTimeMillis() - lastClick < 300)
            MinecraftClient.getInstance().openScreen(HUDConfigureGUI(i))
        lastClick = System.currentTimeMillis()
        return true
    }

    override fun mouseDragged(x1: Double, y1: Double, button: Int, x2: Double, y2: Double): Boolean {
        super.mouseDragged(x1, y1, button, x2, y2)
        val i = selectedItem ?: return true
        val bounds = HUD.hudItems[i.id]?.getBounds(i.config)
        val pos = i.config.position
        if (bounds != null && x1.toInt() in pos.first until pos.first + bounds.width && y1.toInt() in pos.second until pos.second + bounds.height) {
            var tx = x1.toInt() - diffX
            var ty = y1.toInt() - diffY
            if (!hasControlDown())
                HUD.config.items.forEach {
                    if (it == selectedItem) return@forEach
                    // Grid snap
                    val ip = it.config.position
                    val bounds = HUD.hudItems[i.id]?.getBounds(it.config)
                    if ((ty - ip.second).absoluteValue <= 5) {
                        ty = ip.second
                    }
                    if ((tx - ip.first).absoluteValue <= 5) {
                        tx = ip.first
                    }
                }
            i.config.position = tx to ty
        }
        return true
    }

    override fun keyPressed(key: Int, int_2: Int, int_3: Int): Boolean {
        super.keyPressed(key, int_2, int_3)
        val i = selectedItem ?: return true
        val pos = i.config.position
        when (key) {
            264 -> i.config.position = pos.first to pos.second + 1 // Down
            263 -> i.config.position = pos.first - 1 to pos.second // Left
            262 -> i.config.position = pos.first + 1 to pos.second // Right
            265 -> i.config.position = pos.first to pos.second - 1 // Up
        }
        return true
    }
}