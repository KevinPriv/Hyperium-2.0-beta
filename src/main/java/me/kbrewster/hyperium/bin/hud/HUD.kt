package me.kbrewster.hyperium.bin.hud

import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.bin.Feature
import me.kbrewster.hyperium.bin.hud.gui.HUDConfigureGUI
import me.kbrewster.hyperium.bin.hud.gui.HUDGui
import me.kbrewster.hyperium.bin.hud.items.FPSHUDItem
import me.kbrewster.hyperium.bin.hud.items.PosHUDItem
import me.kbrewster.hyperium.events.ClientChatEvent
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import net.minecraft.client.MinecraftClient
import java.awt.Color

object HUD : Feature() {
    override val metadata = Metadata("HUD", 1, listOf("Cubxity"))

    val hudItems = mutableMapOf(
            "hyperium.position" to PosHUDItem(),
            "hyperium.fps" to FPSHUDItem()
    )
    val config = Config() // This is temp TODO: Saving/Loading
    var t = 0
    var pending = false
    var rgb: Int = 0xffffffff.toInt()
    var h = 0.0f

    @Subscribe
    fun render(e: OnGuiHudRenderEvent) {
        if (pending)
            if (t == 2) {
                MinecraftClient.getInstance().openScreen(HUDGui())
                pending = false
            } else t++
        if (MinecraftClient.getInstance().currentScreen !is HUDConfigureGUI)
            config.items.forEach {
                with(it.config) {
                    hudItems[it.id]?.render(position, alignment, this)
                }
            }
        if (h > 1f)
            h = 0f
        h += 0.005f
        rgb = Color.getHSBColor(h, 0.8f, 0.8f).rgb
    }

    @Subscribe
    fun onChat(e: ClientChatEvent) {
        if (e.message == "/hud") { // temp TODO: Actual command system
            e.cancelled = true
            pending = true
        }
    }
}