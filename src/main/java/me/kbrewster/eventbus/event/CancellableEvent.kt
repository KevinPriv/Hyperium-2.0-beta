package me.kbrewster.eventbus.event

open class CancellableEvent: AbstractEvent() {

    var cancelled = false
        @JvmName("isCancelled") get

}