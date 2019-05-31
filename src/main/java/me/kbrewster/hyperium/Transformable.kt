package me.kbrewster.hyperium

import me.falsehonesty.asmhelper.dsl.AsmWriter

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