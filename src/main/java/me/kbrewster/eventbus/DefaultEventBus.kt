package me.kbrewster.eventbus

import java.util.concurrent.CopyOnWriteArrayList

class DefaultEventBus : AbstractEventBus() {

    private val subscriptions = HashMap<Class<*>, CopyOnWriteArrayList<SubscriberData>>()

    override fun register(obj: Any) {
        val clazz = obj.javaClass
        // iterates though all the methods in the class
        for (method in clazz.methods) {
            // all the information and error checking before the method is added such
            // as if it even is an event before the element even touches the HashMap
            method.getAnnotation(Subscribe::class.java) ?: continue
            val event = method.parameters.first().type ?: throw
            IllegalArgumentException("Couldn't find parameter inside of ${method.name}!")
            val priority = method.getAnnotation(Subscribe::class.java).priority
            method.isAccessible = true

            // where the method gets added to the event key inside of the subscription HashMap
            // the ArrayList is either sorted or created before the element is added
            this.subscriptions.let { subs ->
                if (subs.containsKey(event)) {
                    // sorts array on insertion
                    subs[event]?.add(SubscriberData(obj, method, priority))
                    subs[event]?.sortByDescending { it.priority }
                } else {
                    // event hasn't been added before so it creates a new instance
                    // sorting does not matter here since there is no other elements to compete against
                    subs[event] = CopyOnWriteArrayList()
                    subs[event]?.add(SubscriberData(obj, method, priority))
                }
            }
        }
    }

    override fun registerAll(vararg obj: Any) =
        obj.forEach(this::register)

    override fun registerAll(obj: Iterable<Any>) =
        obj.forEach(this::register)

    override fun unregister(obj: Any) =
        this.subscriptions.values.forEach { map ->
            map.removeIf { it.instance == obj }
        }

    override fun unregisterAll(vararg obj: Any) =
        obj.forEach(this::unregister)

    override fun unregisterAll(obj: Iterable<Any>) =
        obj.forEach(this::unregister)

    override fun post(event: Any) {
        this.subscriptions[event.javaClass]
            ?.forEach { sub -> sub.method.invoke(sub.instance, event) }
    }
}