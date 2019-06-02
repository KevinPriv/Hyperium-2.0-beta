package me.kbrewster.hyperium.asm

import me.kbrewster.hyperium.Inaccessible
import me.kbrewster.hyperium.asm.transformers.ChatScreenTransformer
import me.kbrewster.hyperium.asm.transformers.InGameHudTransformer
import me.kbrewster.hyperium.asm.transformers.MinecraftClientTransformer
import org.apache.logging.log4j.LogManager
import java.io.File
import java.lang.instrument.Instrumentation
import java.net.URL

@Inaccessible
object HyperiumAgent {

    private val logger = LogManager.getLogger("Agent")

    val transformers = listOf(MinecraftClientTransformer, InGameHudTransformer, ChatScreenTransformer)

    /**
     * TODO
     *
     * @param arguments
     * @param instrumentation
     */
    @JvmStatic
    fun premain(arguments: String?, instrumentation: Instrumentation) {
        logger.info("[Agent] Hyperium Agent has been loaded, transforming classes.. ")
        instrumentation.addTransformer(ClassTransformer)
    }

    /**
     * Adds the compiled jar to the same [ClassLoader] as [MinecraftClient]
     * This must go through or else Hyperium may not be able to access any of Minecrafts methods.
     *
     * @param classLoader The classloader we are adding the jar to
     */
    @JvmStatic
    fun switchClassloader(classLoader: ClassLoader) {
        val uri = HyperiumAgent::class.java.protectionDomain.codeSource.location.toURI()

        try {
            // Java 1.8 down
            classLoader::class.java.getDeclaredMethod("addURL", URL::class.java).run {
                isAccessible = true
                invoke(classLoader, File(uri).toURI().toURL())
            }
        } catch (e: NoSuchMethodException) {
            // Java 1.9 up
            classLoader::class.java
                    .getDeclaredMethod("appendToClassPathForInstrumentation", String::class.java).run {
                        isAccessible = true
                        invoke(classLoader, uri.toURL())
                    }
        }
    }
}
