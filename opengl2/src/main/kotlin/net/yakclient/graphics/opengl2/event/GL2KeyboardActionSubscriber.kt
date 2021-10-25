package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*
import org.lwjgl.input.Keyboard

@Provides(KeyboardActionSubscriber::class)
public class GL2KeyboardActionSubscriber : KeyboardActionSubscriber(), Ticking {
    override fun hook(): Unit = TickManager.register(this)

    override fun tick() {
        while (Keyboard.next()) {
            notify(KeyActionData(Keyboard.getEventKey(), Keyboard.getEventKeyState()))
        }
    }
}