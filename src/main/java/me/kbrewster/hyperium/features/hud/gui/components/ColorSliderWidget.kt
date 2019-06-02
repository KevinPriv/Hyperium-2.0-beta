package me.kbrewster.hyperium.features.hud.gui.components

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.SliderWidget

/**
 * @author Cubxity
 * @since 6/2/2019
 */
class ColorSliderWidget(x: Int, y: Int, width: Int, height: Int, val name: String, init: Int, val callback: (Int) -> Unit = {}) : SliderWidget(MinecraftClient.getInstance().options, x, y, width, height, (init / 255).toDouble()) {

    init {
        updateMessage()
    }

    override fun updateMessage() {
        message = "$name: ${(value * 255).toInt()}"
    }

    override fun applyValue() = callback((value * 255).toInt())
}