@file:JvmName("Events")

package net.yakclient.graphics.api.event

public val onMouseMove: Class<MouseMoveDispatcher> = MouseMoveDispatcher::class.java

public val onMouseClick: Class<MouseButtonEventDispatcher> = MouseButtonEventDispatcher::class.java

public val onKeyboardAction: Class<KeyboardActionDispatcher> = KeyboardActionDispatcher::class.java

public data class MouseMoveData(
    public val x: Int,
    public val y: Int,
    public val deltaX: Int,
    public val deltaY: Int,
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