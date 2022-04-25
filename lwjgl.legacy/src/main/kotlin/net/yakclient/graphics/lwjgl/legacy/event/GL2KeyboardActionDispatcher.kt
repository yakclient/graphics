package net.yakclient.graphics.lwjgl.legacy.event

import net.yakclient.graphics.api.event.KeyboardActionData
import net.yakclient.graphics.api.event.KeyboardActionDispatcher
import net.yakclient.graphics.api.event.TickManager
import net.yakclient.graphics.api.event.Ticking
import org.lwjgl.input.Keyboard

public class GL2KeyboardActionDispatcher : KeyboardActionDispatcher(), Ticking {
    init {
        TickManager.register(this)
    }

    override fun tick() {
        while (Keyboard.next()) {
            dispatch(KeyboardActionData(Keyboard.getEventKey(), Keyboard.getEventKeyState()))
        }
    }
}