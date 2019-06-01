package me.kbrewster.hyperium.transformers

import me.falsehonesty.asmhelper.dsl.At
import me.falsehonesty.asmhelper.dsl.InjectionPoint
import me.falsehonesty.asmhelper.dsl.inject
import me.falsehonesty.asmhelper.dsl.instructions.*
import me.kbrewster.hyperium.Transformable
import me.kbrewster.hyperium.remapper
import net.minecraft.client.gui.hud.InGameHud

object InGameHudTransformer : Transformable {

    val inGameHudName = InGameHud::class.java.name
    val drawName: String by remapper("draw", "")

    val renderStatusEffectOverlayName: String by remapper("renderStatusEffectOverlay", "")

    val draw = inject {
        className = inGameHudName
        methodName = drawName
        at = At(InjectionPoint.INVOKE(Descriptor(inGameHudName.replace(".", "/"), renderStatusEffectOverlayName, "()V")), before = false)

        insnList {
            // local_7 = OnGuiHudRender()
            // Hyperium.INSTANCE.getEventbus().post(local_7)
            createInstance("me/kbrewster/hyperium/events/OnGuiHudRenderEvent", "()V")
            astore(7)
            field(FieldAction.GET_STATIC, "me/kbrewster/hyperium/Hyperium", "INSTANCE", "Lme/kbrewster/hyperium/Hyperium;")
            invoke(InvokeType.VIRTUAL, "me/kbrewster/hyperium/Hyperium", "getEventbus", "()Lme/kbrewster/eventbus/DefaultEventBus;")
            aload(7)
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/DefaultEventBus", "post", "(Ljava/lang/Object;)V")
            // if(local_7.isCancelled()) {
            //  return
            // }
            aload(7)
            invoke(InvokeType.VIRTUAL, "me/kbrewster/eventbus/event/CancellableEvent", "isCancelled", "()Z")
            ifClause(JumpCondition.EQUAL) {
                methodReturn()
            }
        }
    }

    init {
        addTransformer(draw)
    }
}