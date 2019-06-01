package me.kbrewster.hyperium

import me.kbrewster.eventbus.DefaultEventBus
import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.InitialisationEvent
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import me.kbrewster.hyperium.features.FeaturesManager
import me.kbrewster.hyperium.features.hud.HUD
import me.kbrewster.hyperium.transformers.InGameHudTransformer
import me.kbrewster.hyperium.transformers.MinecraftClientTransformer
import org.apache.logging.log4j.LogManager
import java.lang.instrument.Instrumentation

@Suppress("unused")
object Hyperium {
    private val logger = LogManager.getLogger("Hyperium-2.0")
    val features = FeaturesManager()

    @Inaccessible
    val transformers = listOf(MinecraftClientTransformer, InGameHudTransformer)

    val eventbus = DefaultEventBus()

    @JvmStatic
    @Inaccessible
    fun premain(arguments: String?, instrumentation: Instrumentation) {
        logger.info("[Agent] Hyperium Agent has been loaded, transforming classes.. ")
        instrumentation.addTransformer(ClassTransformer)
    }

    @Subscribe(1337)
    fun init(event: InitialisationEvent) {
        logger.info("Successfully entered initialisation!")
        logger.info("Registering features")

        features {
            register(HUD)
        }
    }

    @Subscribe(1337)
    fun render(event: OnGuiHudRenderEvent) {
    }

}
