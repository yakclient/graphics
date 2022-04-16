package net.yakclient.graphics.opengl2.event

import net.yakclient.graphics.api.event.MouseActionData
import net.yakclient.graphics.api.event.MouseButtonEventDispatcher
import net.yakclient.graphics.api.event.TickManager
import net.yakclient.graphics.api.event.Ticking
import org.lwjgl.input.Mouse

public class GL2MouseButtonEventDispatcher : MouseButtonEventDispatcher(), Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        while (Mouse.next()) {
            val key = Mouse.getEventButton()

            if (key != -1)
                dispatch(MouseActionData(key, Mouse.getEventButtonState()))
        }
    }
}