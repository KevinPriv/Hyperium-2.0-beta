package me.kbrewster.hyperium.asm

import net.minecraft.client.MinecraftClient
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object Mappings {
    val obfuscated: Boolean by lazy {
        MinecraftClient::class.java.name != "net.minecraft.client.MinecraftClient"
    }
}

class RemapDelegate<T>(identifier: String, obfIdentifier: String) : ReadOnlyProperty<T, String> {
    val value: String by lazy { if(!Mappings.obfuscated) identifier else obfIdentifier }
    override fun getValue(thisRef: T, property: KProperty<*>): String {
        return value
    }
}

fun <T> remapper(identifier: String, obfIdentifier: String): RemapDelegate<T> = RemapDelegate(identifier, obfIdentifier)