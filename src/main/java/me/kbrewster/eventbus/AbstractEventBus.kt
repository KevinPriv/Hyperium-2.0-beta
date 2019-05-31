package me.kbrewster.eventbus

abstract class AbstractEventBus: EventBus {

    override fun register(obj: Any) {}

    override fun registerAll(vararg obj: Any) {}

    override fun registerAll(obj: Iterable<Any>) {}

    override fun unregister(obj: Any) {}

    override fun unregisterAll(vararg obj: Any) {}

    override fun unregisterAll(obj: Iterable<Any>) {}

    override fun post(event: Any) {}

}