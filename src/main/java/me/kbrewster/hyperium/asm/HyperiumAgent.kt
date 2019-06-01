package me.kbrewster.hyperium.asm

import me.kbrewster.hyperium.ClassTransformer
import me.kbrewster.hyperium.Inaccessible
import me.kbrewster.hyperium.transformers.InGameHudTransformer
import me.kbrewster.hyperium.transformers.MinecraftClientTransformer
import org.apache.logging.log4j.LogManager
import java.io.File
import java.lang.instrument.Instrumentation
import java.net.URL

@Inaccessible
object HyperiumAgent {

    private val logger = LogManager.getLogger("Agent")

    val transformers = listOf(MinecraftClientTransformer, InGameHudTransformer)

    @JvmStatic
    fun premain(arguments: String?, instrumentation: Instrumentation) {
        logger.info("[Agent] HyperiumAgent Agent has been loaded, transforming classes.. ")
        instrumentation.addTransformer(ClassTransformer)
    }

    @JvmStatic
    fun switchClassloader(classLoader: ClassLoader) {
        val uri = HyperiumAgent::class.java.protectionDomain.codeSource.location.toURI()

        try {
            classLoader::class.java.getDeclaredMethod("addURL", URL::class.java).run {
                isAccessible = true
                invoke(classLoader, File(uri).toURI().toURL())
            }
        } catch (e: NoSuchMethodException) {
            classLoader::class.java
                    .getDeclaredMethod("appendToClassPathForInstrumentation", String::class.java).run {
                        isAccessible = true
                        invoke(classLoader, uri.toURL())
                    }
        }


    }


}
