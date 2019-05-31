package me.kbrewster.eventbus

import java.lang.reflect.Method

/**
 * Marks a method as an event subscriber.
 *
 * <p>The type of event will be indicated by the method's first (and only) parameter. If this
 * annotation is applied to methods with zero parameters, or more than one parameter, the object
 * containing the method will not be able to register for event delivery from the {@link me.kbrewster.eventbus.EventBus}.
 *
 * @author Kevin Brewster
 * @since 1.0
 */
annotation class Subscribe(val priority: Int = -1)

data class SubscriberData(val instance: Any, val method: Method, val priority: Int = -1)