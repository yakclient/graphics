package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.hook.HookTickManager
import net.yakclient.graphics.api.hook.MouseButtonEventSubscriber
import net.yakclient.graphics.api.hook.KeyActionData
import net.yakclient.graphics.api.hook.Ticking
import org.lwjgl.input.Mouse

@Provides(MouseButtonEventSubscriber::class)
public class GL2MouseButtonEventSubscriber : MouseButtonEventSubscriber(), Ticking {
    override fun hook(): Unit =
        HookTickManager.register(this)

    override fun tick() {
        while (Mouse.next()) {
            val key = Mouse.getEventButton()

            if (key != -1)
                notify(KeyActionData(key, Mouse.getEventButtonState()))
        }
    }
}