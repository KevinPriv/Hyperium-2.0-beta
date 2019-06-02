package me.kbrewster.hyperium.bin

import me.kbrewster.hyperium.Hyperium
import me.kbrewster.hyperium.process.Process


/**
 * @author Cubxity
 * @since 6/2/2019
 */
abstract class Feature(register: Boolean = true) : Process() {
    abstract val metadata: Metadata

    init {
        if (register)
            Hyperium.eventbus.register(this)
    }

    data class Metadata(val name: String, val version: Int, val authors: List<String>)
}