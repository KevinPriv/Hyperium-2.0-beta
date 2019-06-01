package me.kbrewster.hyperium

import me.falsehonesty.asmhelper.dsl.AsmWriter
import org.apache.logging.log4j.LogManager
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

object ClassTransformer: ClassFileTransformer {

    private val logger = LogManager.getLogger("Class-Transformer")

    val classReplacers = mutableMapOf<String, String>()
    val asmWriters = mutableListOf<AsmWriter>()

    val excludedClasses = mutableListOf<String>()
    val excludedPackages = mutableListOf<String>()


    init {
        excludedClasses += this.javaClass.name
        excludedPackages += "kotlin."
        excludedPackages += "me.falsehonesty.asmhelper."
    }

    override fun transform(classLoader: ClassLoader?, fileName: String?, p2: Class<*>?, p3: ProtectionDomain?, basicClass: ByteArray?): ByteArray? {
    //    println(fileName + "--:--" + classLoader)
        if (basicClass == null || fileName == null) return null

        val className = fileName.replace("/", ".")

        // checks if the class is being excluded from being transformed
        if(excludedPackages.filter { className.startsWith(it) }.any()) {
            return basicClass
        }

        if(className in excludedClasses) {
            return basicClass
        }

        // If the class is being replaced
        classReplacers[className]?.let { classFile ->
            logger.info("Completely replacing {} with data from {}.", className, classFile)

            return loadReplacementClass(classFile)
        }

        // the classes which will get transformed if they have an ASM writer, else will just return the unmodified class
        val writers = asmWriters
                .filter {  it.className == className }
                .ifEmpty { return basicClass }

        // must have an ASM writer so it will now be transformed
        logger.info("Transforming class {}", className)

       return transformClass(basicClass, writers).toByteArray()
    }

    private fun transformClass(basicClass: ByteArray?, writers: List<AsmWriter>): ClassWriter {
        val classReader = ClassReader(basicClass)
        val classNode = ClassNode()
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES)
        writers.forEach {
            logger.info("Applying AsmWriter {}", it)

            it.transform(classNode)
        }

        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        try {
            classNode.accept(classWriter)
        } catch (e: Throwable) {
            logger.error("Exception when transforming {}", e.javaClass.simpleName)
            e.printStackTrace()
        }
        return classWriter
    }

    private fun loadReplacementClass(name: String): ByteArray {
        return this::class.java.classLoader.getResourceAsStream(name).readBytes()
    }
}