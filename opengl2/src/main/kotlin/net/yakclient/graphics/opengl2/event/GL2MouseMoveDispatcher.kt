package net.yakclient.graphics.opengl2.event

import net.yakclient.graphics.api.event.MouseMoveData
import net.yakclient.graphics.api.event.MouseMoveDispatcher
import net.yakclient.graphics.api.event.TickManager
import net.yakclient.graphics.api.event.Ticking

import org.lwjgl.input.Mouse

public class GL2MouseMoveDispatcher : MouseMoveDispatcher(),Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        val dx = Mouse.getDX()
        val dy = Mouse.getDY()
        if (dx > 0 || dy > 0) dispatch(MouseMoveData(Mouse.getX(), Mouse.getY(), dx, dy))
    }
}