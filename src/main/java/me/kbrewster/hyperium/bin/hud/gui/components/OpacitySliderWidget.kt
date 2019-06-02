package me.kbrewster.hyperium.bin.hud.gui.components

import me.kbrewster.hyperium.bin.hud.SavedItem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.SliderWidget

/**
 * @author Cubxity
 * @since 6/2/2019
 */
class OpacitySliderWidget(val item: SavedItem, x: Int, y: Int, width: Int, height: Int) : SliderWidget(MinecraftClient.getInstance().options, x, y, width, height, item.config.opacity) {

    init {
        updateMessage()
    }

    override fun updateMessage() {
        message = "Opacity: $value"
    }

    override fun applyValue() {
        item.config.opacity = value
    }
}