package net.yakclient.graphics.opengl2.hook

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.hook.HookTickManager
import net.yakclient.graphics.api.hook.KeyActionData
import net.yakclient.graphics.api.hook.KeyboardActionSubscriber
import net.yakclient.graphics.api.hook.Ticking
import org.lwjgl.input.Keyboard

@Provides(KeyboardActionSubscriber::class)
public class GL2KeyboardActionSubscriber : KeyboardActionSubscriber(), Ticking {
    override fun hook(): Unit = HookTickManager.register(this)

    override fun tick() {
        while (Keyboard.next()) {
            notify(KeyActionData(Keyboard.getEventKey(), Keyboard.getEventKeyState()))
        }
    }
}