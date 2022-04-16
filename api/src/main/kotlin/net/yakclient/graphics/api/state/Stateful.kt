package net.yakclient.graphics.api.state

import kotlin.reflect.KProperty

public interface Stateful<T> {
    public var value: T

    public operator fun getValue(thisRef: Nothing?, property: KProperty<*>): T = value

    public operator fun setValue(thisRef: Nothing?, property: KProperty<*>, value: T): Unit = let { this.value = value }
}