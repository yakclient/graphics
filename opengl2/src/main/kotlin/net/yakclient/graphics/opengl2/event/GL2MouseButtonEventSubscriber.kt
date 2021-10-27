package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.KeyActionData
import net.yakclient.graphics.api.event.MouseButtonEventSubscriber
import net.yakclient.graphics.api.event.TickManager
import net.yakclient.graphics.api.event.Ticking
import org.lwjgl.input.Mouse

@Provides(MouseButtonEventSubscriber::class)
public class GL2MouseButtonEventSubscriber : MouseButtonEventSubscriber(), Ticking {
    override fun hook(): Unit =
        TickManager.register(this)

    override fun tick() {
        while (Mouse.next()) {
            val key = Mouse.getEventButton()

            if (key != -1)
                notify(KeyActionData(key, Mouse.getEventButtonState()))
        }
    }
}