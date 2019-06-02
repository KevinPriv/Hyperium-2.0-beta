package me.kbrewster.hyperium.events

import me.kbrewster.eventbus.event.CancellableEvent

class InitialisationEvent

class PostInitialisationEvent

class ClientChatEvent(val message: String) : CancellableEvent()