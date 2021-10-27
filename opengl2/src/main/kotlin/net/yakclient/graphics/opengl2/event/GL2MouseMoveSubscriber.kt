package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.MouseMoveData
import net.yakclient.graphics.api.event.MouseMoveSubscriber
import net.yakclient.graphics.api.event.TickManager
import net.yakclient.graphics.api.event.Ticking

import org.lwjgl.input.Mouse

@Provides(MouseMoveSubscriber::class)
public class GL2MouseMoveSubscriber : MouseMoveSubscriber(), Ticking {
    override fun hook(): Unit = TickManager.register(this)

    override fun tick() {
        val dx = Mouse.getDX()
        val dy = Mouse.getDY()
        if (dx > 0 || dy > 0) notify(MouseMoveData(dx, dy, Mouse.getX(), Mouse.getY()))
    }
}