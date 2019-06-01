package me.kbrewster.hyperium.gui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.network.chat.TextComponent


/**
 * @author Cubxity
 * @since 6/1/2019
 */
abstract class HyperiumScreen(name: String) : Screen(TextComponent(name)) {
    fun button(text: String, x: Int, y: Int, width: Int, height: Int, action: (ButtonWidget) -> Unit = {}) {
        addButton(ButtonWidget(x, y, width, height, text) {
            action(it)
        })
    }
}