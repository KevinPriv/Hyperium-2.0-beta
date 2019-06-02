package me.kbrewster.hyperium

import me.kbrewster.eventbus.DefaultEventBus
import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.InitialisationEvent
import me.kbrewster.hyperium.events.OnGuiHudRenderEvent
import me.kbrewster.hyperium.process.TaskManager
import net.minecraft.client.MinecraftClient
import me.kbrewster.hyperium.events.PostInitialisationEvent
import me.kbrewster.hyperium.features.FeaturesManager
import me.kbrewster.hyperium.features.hud.HUD
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
        TaskManager.onInitialisation()
    }

    @Subscribe(1337)
    fun postInit(event: PostInitialisationEvent) {
        logger.info("Registering features")

        features {
            register(HUD)
        }
    }
}