@file:JvmName("Events")

package net.yakclient.graphics.api.event

import net.yakclient.event.api.EventData
import net.yakclient.event.api.EventDispatcher
import net.yakclient.graphics.util.unit.ScreenUnit

public val onMouseMove: Class<MouseMoveDispatcher> = MouseMoveDispatcher::class.java

public val onMouseClick: Class<MouseButtonEventDispatcher> = MouseButtonEventDispatcher::class.java

public val onKeyboardAction: Class<KeyboardActionDispatcher> = KeyboardActionDispatcher::class.java

public data class MouseMoveData(
    public val x: Float,
    public val y: Float,
    public val deltaX: Float,
    public val deltaY: Float,
) : EventData

public abstract class MouseMoveDispatcher : EventDispatcher<MouseMoveData>() {
    override val eventType: Class<MouseMoveData> = MouseMoveData::class.java
}

public interface KeyActionData : EventData {
    public val key: Int
    //false up, true down
    public val state: Boolean
}

public data class MouseActionData(
    override val key: Int,
    override val state: Boolean
) : KeyActionData

public data class KeyboardActionData(
    override val key: Int,
    override val state: Boolean
) : KeyActionData


public abstract class MouseButtonEventDispatcher : EventDispatcher<KeyActionData>() {
    override val eventType: Class<KeyActionData> = KeyActionData::class.java
}

public abstract class KeyboardActionDispatcher : EventDispatcher<KeyActionData>() {
    override val eventType: Class<KeyActionData> = KeyActionData::class.java
}