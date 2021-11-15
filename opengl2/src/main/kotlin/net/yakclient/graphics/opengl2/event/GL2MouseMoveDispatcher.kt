package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*

import org.lwjgl.input.Mouse

@Provides(MouseMoveDispatcher::class)
public class GL2MouseMoveDispatcher : MouseMoveDispatcher(), Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        val dx = Mouse.getDX()
        val dy = Mouse.getDY()
        if (dx > 0 || dy > 0) dispatch(MouseMoveData(dx, dy, Mouse.getX(), Mouse.getY()))
    }
}