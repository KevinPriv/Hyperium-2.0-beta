package me.kbrewster.hyperium.features.hud

import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import me.kbrewster.hyperium.features.AbstractFeature

object HUD : AbstractFeature() {
    override val metadata = Metadata("HUD", "Cubxity", 1)

    init {

    }

    @Subscribe
    fun render(e: OnGuiHudRenderEvent) {

    }
}