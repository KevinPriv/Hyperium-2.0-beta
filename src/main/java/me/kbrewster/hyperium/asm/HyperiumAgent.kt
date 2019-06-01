package me.kbrewster.hyperium.asm

import me.kbrewster.hyperium.ClassTransformer
import me.kbrewster.hyperium.Inaccessible
import me.kbrewster.hyperium.transformers.InGameHudTransformer
import me.kbrewster.hyperium.transformers.MinecraftClientTransformer
import org.apache.logging.log4j.LogManager
import java.io.File
import java.lang.instrument.Instrumentation
import java.net.URL
import java.net.URLClassLoader
import java.lang.reflect.AccessibleObject.setAccessible
import java.net.URLEncoder


object HyperiumAgent {

    private val logger = LogManager.getLogger("Agent")

    @Inaccessible
    val transformers = listOf(MinecraftClientTransformer, InGameHudTransformer)

    @JvmStatic
    @Inaccessible
    fun premain(arguments: String?, instrumentation: Instrumentation) {
        logger.info("[Agent] HyperiumAgent Agent has been loaded, transforming classes.. ")
        instrumentation.addTransformer(ClassTransformer)
    }

    @JvmStatic
    fun switchClassloader(classLoader: ClassLoader) {
        val uri = HyperiumAgent::class.java.protectionDomain.codeSource.location.toURI()
        try {
            val method = classLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.isAccessible = true
            println(File(HyperiumAgent::class.java.protectionDomain.codeSource.location.toURI()))
            method.invoke(classLoader, File(uri).toURL())
        } catch (e: NoSuchMethodException) {
            val method = classLoader::class.java
                    .getDeclaredMethod("appendToClassPathForInstrumentation", String::class.java)
            method.isAccessible = true
           method.invoke(classLoader, uri.toURL())
        }


    }



}
