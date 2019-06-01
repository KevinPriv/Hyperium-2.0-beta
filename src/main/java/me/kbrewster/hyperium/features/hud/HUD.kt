package me.kbrewster.hyperium.features.hud

import me.kbrewster.eventbus.Subscribe
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

    init {
    }

    @Subscribe
    fun render(e: OnGuiHudRenderEvent) {
        if (t == 100) {
            MinecraftClient.getInstance().openScreen(HUDGui())
        }
        t++
        config.packs.forEach {
            it.items.forEach { i ->
                with(i) {
                    hudItems[id]?.render(it.position, it.alignment, i.config)
                }
            }
        }
    }
}