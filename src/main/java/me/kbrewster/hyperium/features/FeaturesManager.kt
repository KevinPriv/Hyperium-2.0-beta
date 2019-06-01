package me.kbrewster.hyperium.features

import me.kbrewster.hyperium.Hyperium
import org.apache.logging.log4j.LogManager

/**
 * @author Cubxity
 * @since 6/1/2019
 */
class FeaturesManager {
    private val logger = LogManager.getLogger("Features")
    private val features = mutableListOf<AbstractFeature>()

    fun register(feature: AbstractFeature, configure: AbstractFeature.() -> Unit = {}) {
        val (name, author, version) = feature.metadata
        logger.info("Registering $name by $author (version: $version)")
        Hyperium.eventbus.register(feature.apply(configure))
        features += feature
    }

    fun unregister(feature: AbstractFeature) {
        val (name) = feature.metadata
        logger.info("Unregistering $name")
        Hyperium.eventbus.unregister(feature)
        feature.unregister()
        features -= feature
    }

    /**
     * This should not be used for registering / unregistering features
     */
    fun getFeatures(): List<AbstractFeature> = features

    operator fun invoke(opt: FeaturesManager.() -> Unit) = apply(opt)
}