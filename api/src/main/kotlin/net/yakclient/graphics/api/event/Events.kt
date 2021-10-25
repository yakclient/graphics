@file:JvmName("Events")

package net.yakclient.graphics.api.event

public val onMouseMove: Class<MouseMoveSubscriber> = MouseMoveSubscriber::class.java

public val onMouseClick: Class<MouseButtonEventSubscriber> = MouseButtonEventSubscriber::class.java

public val onKeyboardAction: Class<KeyboardActionSubscriber> = KeyboardActionSubscriber::class.java

public data class MouseMoveData(
    public val x: Int,
    public val y: Int,
    public val absoluteX: Int,
    public val absoluteY: Int,
) : EventData

public abstract class MouseMoveSubscriber : EventSubscriber<MouseMoveData>()

public data class KeyActionData(
    public val key: Int,
    //True up, False down
    public val state: Boolean
) : EventData

public abstract class MouseButtonEventSubscriber : EventSubscriber<KeyActionData>()

public abstract class KeyboardActionSubscriber : EventSubscriber<KeyActionData>()