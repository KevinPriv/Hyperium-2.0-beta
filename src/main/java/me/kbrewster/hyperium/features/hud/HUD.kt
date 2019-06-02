package me.kbrewster.hyperium.features.hud

import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.ClientChatEvent
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import me.kbrewster.hyperium.features.AbstractFeature
import me.kbrewster.hyperium.features.hud.gui.HUDGui
import me.kbrewster.hyperium.features.hud.items.FPSHUDItem
import me.kbrewster.hyperium.features.hud.items.PosHUDItem
import net.minecraft.client.MinecraftClient

object HUD : AbstractFeature() {
    override val metadata = Metadata("HUD", "Cubxity", 1)
    val config = Config() // This is temp TODO: Saving/Loading
    val hudItems = mutableMapOf(
            "hyperium.position" to PosHUDItem(),
            "hyperium.fps" to FPSHUDItem()
    )
    var t = 0
    var pending = false

    init {
    }

    @Subscribe
    fun render(e: OnGuiHudRenderEvent) {
        if (pending)
            if (t == 2) {
                MinecraftClient.getInstance().openScreen(HUDGui())
                pending = false
            } else t++
        config.items.forEach {
            with(it.config) {
                hudItems[it.id]?.render(position, alignment, this)
            }
        }
    }

    @Subscribe
    fun onChat(e: ClientChatEvent) {
        if (e.message == "/hud") { // temp TODO: Actual command system
            e.cancelled = true
            pending = true
        }
    }
}