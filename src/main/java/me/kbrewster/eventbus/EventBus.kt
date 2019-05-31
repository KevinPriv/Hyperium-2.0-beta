package me.kbrewster.eventbus

interface EventBus {

    fun register(obj: Any)

    fun registerAll(vararg obj: Any)

    fun registerAll(obj: Iterable<Any>)

    fun unregister(obj: Any)

    fun unregisterAll(vararg obj: Any)

    fun unregisterAll(obj: Iterable<Any>)

    fun post(event: Any)
}