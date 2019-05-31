package me.falsehonesty.asmhelper.dsl.instructions

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode

enum class FieldAction(val opcode: Int) {
    GET_STATIC(Opcodes.GETSTATIC),
    PUT_STATIC(Opcodes.PUTSTATIC),
    GET_FIELD(Opcodes.GETFIELD),
    PUT_FIELD(Opcodes.PUTFIELD)
}

fun InsnListBuilder.field(action: FieldAction, descriptor: Descriptor) = this.field(action, descriptor.owner, descriptor.name, descriptor.desc)

fun InsnListBuilder.field(action: FieldAction, owner: String, name: String, desc: String) {
    insnList.add(FieldInsnNode(
        action.opcode,
        owner,
        name,
        desc
    ))
}

fun InsnListBuilder.getField(descriptor: Descriptor) {
    field(FieldAction.GET_FIELD, descriptor)
}

fun InsnListBuilder.updateField(descriptor: Descriptor, updater: InsnListBuilder.() -> Unit) {
    getField(descriptor)

    this.updater()

    field(FieldAction.PUT_FIELD, descriptor)
}

fun InsnListBuilder.setField(descriptor: Descriptor) {
    field(FieldAction.PUT_FIELD, descriptor)
}