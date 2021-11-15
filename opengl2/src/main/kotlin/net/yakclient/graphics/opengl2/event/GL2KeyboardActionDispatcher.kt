package net.yakclient.graphics.opengl2.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*
import org.lwjgl.input.Keyboard

@Provides(KeyboardActionDispatcher::class)
public class GL2KeyboardActionDispatcher : KeyboardActionDispatcher(), Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        while (Keyboard.next()) {
            dispatch(KeyActionData(Keyboard.getEventKey(), Keyboard.getEventKeyState()))
        }
    }
}