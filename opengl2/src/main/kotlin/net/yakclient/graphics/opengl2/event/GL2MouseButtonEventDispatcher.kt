package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*
import org.lwjgl.input.Mouse

@Provides(MouseButtonEventDispatcher::class)
public class GL2MouseButtonEventDispatcher : MouseButtonEventDispatcher(), Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        while (Mouse.next()) {
            val key = Mouse.getEventButton()

            if (key != -1)
                dispatch(KeyActionData(key, Mouse.getEventButtonState()))
        }
    }
}