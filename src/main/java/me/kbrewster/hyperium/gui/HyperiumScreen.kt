package me.kbrewster.hyperium.gui

import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
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

    fun textField(x: Int, y: Int, width: Int, height: Int, init: String, opt: TextFieldWidget.() -> Unit = {}) {
        addButton(TextFieldWidget(font, x, y, width, height, init).apply(opt).also {
            it.text = init
        })
    }
}