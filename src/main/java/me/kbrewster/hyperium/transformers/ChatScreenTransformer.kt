package me.kbrewster.hyperium.transformers

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*
import me.kbrewster.hyperium.Transformable
import me.kbrewster.hyperium.remapper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ChatScreen
import net.minecraft.client.gui.screen.Screen
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnNode

object ChatScreenTransformer : Transformable {
    val chatScreenName = ChatScreen::class.java.name
    val screenName = Screen::class.java.name
    val minecraftClientName = MinecraftClient::class.java.name

    val keyPressedName by remapper("keyPressed", "")
    val sendMessageName by remapper("sendMessage", "")
    val minecraftName by remapper("minecraft", "")
    val openScreenName by remapper("openScreen", "")

    val keyPressed = inject {
        className = chatScreenName
        methodName = keyPressedName
        at = At(InjectionPoint.INVOKE(Descriptor(chatScreenName.replace('.', '/'), sendMessageName, "(Ljava/lang/String;)V")), shift = -2) // before ALOAD 0 instruction

        insnList {
            // Hyperium.INSTANCE.getEventbus().post(ClientChatEvent(local_4))
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
            createInstance("me/kbrewster/hyperium/events/ClientChatEvent", "(Ljava/lang/String;)V") {
                aload(4)
            }
            dup()
            astore(5)
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "post", "(Ljava/lang/Object;)V")

            // if (stack_0.isCancelled() != 0)
            aload(5)
            invoke(InvokeType.VIRTUAL, Descriptor("me/kbrewster/eventbus/event/CancellableEvent", "isCancelled", "()Z"))
            ifClause(JumpCondition.EQUAL) {
                //this.minecraft.openScreen(null)
                aload(0)
                getField(Descriptor(chatScreenName.replace('.', '/'), minecraftName, "L${minecraftClientName.replace('.', '/')};"))
                aconst_null()
                invoke(InvokeType.VIRTUAL, Descriptor(minecraftClientName.replace('.', '/'), openScreenName, "(L${screenName.replace('.', '/')};)V"))
                insn(InsnNode(Opcodes.ICONST_1))
                ireturn()
            }
        }
    }

    init {
        addTransformer(keyPressed)
    }
}