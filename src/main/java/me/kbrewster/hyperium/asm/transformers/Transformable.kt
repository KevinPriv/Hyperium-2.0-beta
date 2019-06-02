package me.kbrewster.hyperium.asm.transformers

import me.falsehonesty.asmhelper.dsl.AsmWriter
import me.kbrewster.hyperium.Inaccessible
import me.kbrewster.hyperium.asm.ClassTransformer

@Inaccessible
interface Transformable {

    fun addTransformer(writer: AsmWriter) {
        ClassTransformer.asmWriters.add(writer)
    }

    fun addTransformers(vararg writers: AsmWriter) {
        ClassTransformer.asmWriters.addAll(writers)
    }

    fun addTransformers(writers: List<AsmWriter>) {
        ClassTransformer.asmWriters.addAll(writers)
    }

}