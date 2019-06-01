package me.kbrewster.hyperium

import me.kbrewster.eventbus.DefaultEventBus
import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.InitialisationEvent
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import net.minecraft.client.MinecraftClient
import me.kbrewster.hyperium.features.FeaturesManager
import me.kbrewster.hyperium.features.hud.HUD
import me.kbrewster.hyperium.transformers.InGameHudTransformer
import me.kbrewster.hyperium.transformers.MinecraftClientTransformer
import org.apache.logging.log4j.LogManager

@Suppress("unused")
object Hyperium {
    private val logger = LogManager.getLogger("Hyperium-2.0")
    val features = FeaturesManager()

    val eventbus = DefaultEventBus()

    @Subscribe(1337)
    fun init(event: InitialisationEvent) {
        logger.info("Successfully entered initialisation!")
        logger.debug("Launched on the {} classloader.".format(this.javaClass.classLoader.toString()))
        logger.info("Registering features")

        features {
            register(HUD)
        }
    }

    @Subscribe(1337)
    fun render(event: OnGuiHudRenderEvent) {
        MinecraftClient.getInstance().gameRenderer.client.inGameHud.fontRenderer.drawWithShadow("test", 1F, 1F, 0xFFFFFF)
    }
}