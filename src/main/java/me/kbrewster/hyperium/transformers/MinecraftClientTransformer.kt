package me.kbrewster.hyperium.transformers

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*
import me.kbrewster.hyperium.Transformable
import me.kbrewster.hyperium.remapper
import net.minecraft.client.MinecraftClient

object MinecraftClientTransformer : Transformable {

    val minecraftClientName = MinecraftClient::class.java.name
    val startName: String by remapper("start", "")
    val initName: String by remapper("init", "")

    val start = inject {
        className = minecraftClientName
        methodName = startName
        at = At(InjectionPoint.HEAD)

        insnList {
            // TODO: Delete or replace with logger lmao
            // System.out.println("Successfully launched Hyperium 2.0!")
            field(FieldAction.GET_STATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
            ldc("Successfully launched Hyperium 2.0!")
            invoke(InvokeType.VIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V")

            // Hyperium.INSTANCE.getEventbus().register(Hyperium.INSTANCE)
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            astore(8)
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
            aload(8)
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "register", "(Ljava/lang/Object;)V")

            // Hyperium.INSTANCE.getEventbus().post(InitialisationEvent())
            createInstance("me/kbrewster/hyperium/events/InitialisationEvent", "()V")
            astore(7)
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
            aload(7)
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "post", "(Ljava/lang/Object;)V")

        }
    }

    init {
        addTransformer(start)
    }

}