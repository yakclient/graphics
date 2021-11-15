@file:JvmName("Events")

package net.yakclient.graphics.api.event

public val onMouseMove: Class<MouseMoveDispatcher> = MouseMoveDispatcher::class.java

public val onMouseClick: Class<MouseButtonEventDispatcher> = MouseButtonEventDispatcher::class.java

public val onKeyboardAction: Class<KeyboardActionDispatcher> = KeyboardActionDispatcher::class.java

public data class MouseMoveData(
    public val x: Int,
    public val y: Int,
    public val absoluteX: Int,
    public val absoluteY: Int,
) : EventData

public abstract class MouseMoveDispatcher : EventDispatcher<MouseMoveData>() {
    override val eventType: Class<MouseMoveData> = MouseMoveData::class.java
}

public data class KeyActionData(
    public val key: Int,
    //false up, true down
    public val state: Boolean
) : EventData

public abstract class MouseButtonEventDispatcher : EventDispatcher<KeyActionData>() {
    override val eventType: Class<KeyActionData> = KeyActionData::class.java
}

public abstract class KeyboardActionDispatcher : EventDispatcher<KeyActionData>() {
    override val eventType: Class<KeyActionData> = KeyActionData::class.java
}