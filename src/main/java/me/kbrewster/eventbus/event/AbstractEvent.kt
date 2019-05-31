package me.kbrewster.eventbus.event

open class AbstractEvent {

    override fun toString(): String {
        return """
            |${this.javaClass.name} {
            |   ${this.javaClass.fields.joinToString(separator = ",\n|   ", postfix = ";") { "${it.name}=${it.get(this)}" }}
            |}
            """.trimMargin()
    }
}