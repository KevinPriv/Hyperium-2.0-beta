package me.kbrewster.hyperium.asm.transformers

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*
import me.kbrewster.hyperium.asm.remapper
import net.minecraft.client.MinecraftClient

object MinecraftClientTransformer: Transformable {

    val minecraftClientName = MinecraftClient::class.java.name
    val startName: String by remapper("start", "")
    val tickName: String by remapper("tick", "")

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

                // HyperiumAgent.INSTANCE.switchClassloader(Thread.currentThread().getContextClassLoader)
                invoke(InvokeType.STATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;")
                invoke(InvokeType.VIRTUAL, "java/lang/Thread", "getContextClassLoader", "()Ljava/lang/ClassLoader;")
                invoke(InvokeType.STATIC, "me/kbrewster/hyperium/asm/HyperiumAgent", "switchClassloader", "(Ljava/lang/ClassLoader;)V")

                //Hyperium.INSTANCE
                field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
                astore(8)
                field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
                invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
                aload(8)
                invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "register", "(Ljava/lang/Object;)V")

                // Hyperium.INSTANCE.getEventbus().post(InitialisationEvent())
                field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
                invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
                createInstance("me/kbrewster/hyperium/events/InitialisationEvent", "()V")
                invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "post", "(Ljava/lang/Object;)V")

            }
        }

    val tick = inject {
        className = minecraftClientName
        methodName = tickName
        at = At(InjectionPoint.RETURN)

        insnList {
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
            createInstance("me/kbrewster/hyperium/events/ClientTickEvent", "()V")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "post", "(Ljava/lang/Object;)V")
        }
    }

    init {
        addTransformers(start, tick)
    }

}