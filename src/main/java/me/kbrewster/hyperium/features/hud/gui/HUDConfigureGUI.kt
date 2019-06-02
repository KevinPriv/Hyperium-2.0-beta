package me.kbrewster.hyperium.features.hud.gui

import me.kbrewster.hyperium.features.hud.HUD
import me.kbrewster.hyperium.features.hud.SavedItem
import me.kbrewster.hyperium.features.hud.gui.components.BackgroundOpacitySliderWidget
import me.kbrewster.hyperium.features.hud.gui.components.ColorSliderWidget
import me.kbrewster.hyperium.features.hud.gui.components.OpacitySliderWidget
import me.kbrewster.hyperium.gui.HyperiumScreen
import net.minecraft.client.MinecraftClient
import java.awt.Color

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class HUDConfigureGUI(val item: SavedItem) : HyperiumScreen("HUD Config") {

    override fun init() {
        // Config
        var y = height / 2 - 100
        val conf = item.config
        addButton(OpacitySliderWidget(item, width / 2 - 100, y, 200, 20))
        y += 25
        button("Background: ${toText(conf.background)}", width / 2 - 100, y, 200, 20) {
            conf.background = !conf.background
            it.message = "Background: ${toText(conf.background)}"
        }
        y += 25
        addButton(BackgroundOpacitySliderWidget(item, width / 2 - 100, y, 200, 20))
        y += 25
        var pickerIndex = 0
        fun pickerName() = when (pickerIndex) {
            0 -> "RGB"
            1 -> "HSB"
            else -> "ERROR"
        }
        button("Color picker: ${pickerName()}", width / 2 - 100, y, 200, 20) {
            if (pickerIndex != 1)
                pickerIndex++
            else pickerIndex = 0
            it.message = "Color picker: ${pickerName()}"
            resize(MinecraftClient.getInstance(), width, height)
        }
        y += 25
        when (pickerIndex) {
            0 -> {
                var col = Color(conf.color)
                addButton(ColorSliderWidget(width / 2 - 100, y, 200, 20, "Red", col.red) {
                    col = Color(it, col.green, col.blue)
                    conf.color = col.rgb
                })
                y += 25
                addButton(ColorSliderWidget(width / 2 - 100, y, 200, 20, "Green", col.green) {
                    col = Color(col.red, it, col.blue)
                    conf.color = col.rgb
                })
                y += 25
                addButton(ColorSliderWidget(width / 2 - 100, y, 200, 20, "Blue", col.blue) {
                    col = Color(col.red, col.green, it)
                    conf.color = col.rgb
                })
                y += 25
            }
            1 -> {

            }
        }
        // Nav
        button("Delete", width / 2 - 70, height - 50, 140, 20) {
            HUD.config.items.remove(item)
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
        button("Back", width / 2 - 70, height - 30, 140, 20) {
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
    }

    private fun toText(bool: Boolean) = if (bool) "ON" else "OFF"

    override fun render(x: Int, y: Int, partialTicks: Float) {
        super.render(x, y, partialTicks)
        with(item.config) {
            val bounds = HUD.hudItems[item.id]?.getBounds(this) ?: return@with
            HUD.hudItems[item.id]?.render(width / 2 - bounds.width / 2 to 50, alignment, this)
        }
    }
}