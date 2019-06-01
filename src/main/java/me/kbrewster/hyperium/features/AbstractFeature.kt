package me.kbrewster.hyperium.features

/**
 * Represents a feature
 */
abstract class AbstractFeature {
    abstract val metadata: Metadata

    /**
     * This function will be called upon unregister
     */
    fun unregister() {}

    data class Metadata(val name: String, val author: String, val version: Int)
}