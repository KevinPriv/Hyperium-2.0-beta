package me.kbrewster.hyperium

import me.kbrewster.eventbus.DefaultEventBus
import me.kbrewster.eventbus.Subscribe
import me.kbrewster.hyperium.events.InitialisationEvent
import me.kbrewster.hyperium.process.TaskManager
import org.apache.logging.log4j.LogManager

object Hyperium {

    private val logger = LogManager.getLogger("Hyperium-2.0")

    val eventbus = DefaultEventBus()


    @Subscribe(1337)
    fun init(event: InitialisationEvent) {
        logger.info("Successfully entered initialisation!")
        logger.debug("Launched on the {} classloader.".format(this.javaClass.classLoader.toString()))
        TaskManager.onInitialisation()
    }

}