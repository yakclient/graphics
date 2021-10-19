package net.yakclient.graphics.opengl2.hook

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.hook.HookTickManager
import net.yakclient.graphics.api.hook.MouseMoveData
import net.yakclient.graphics.api.hook.MouseMoveSubscriber
import net.yakclient.graphics.api.hook.Ticking
import org.lwjgl.input.Mouse

@Provides(MouseMoveSubscriber::class)
public class GL2MouseMoveSubscriber : MouseMoveSubscriber(), Ticking {
    override fun hook(): Unit = HookTickManager.register(this)

    override fun tick() {
        val dx = Mouse.getDX()
        val dy = Mouse.getDY()
        if (dx > 0 || dy > 0) notify(MouseMoveData(dx, dy, Mouse.getX(), Mouse.getY()))
    }
}