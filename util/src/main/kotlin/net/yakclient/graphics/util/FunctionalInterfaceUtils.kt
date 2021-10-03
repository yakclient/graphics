package net.yakclient.graphics.util

import java.util.function.Consumer
import java.util.function.Supplier

public operator fun Runnable.invoke() : Unit = run()

public operator fun <T> Consumer<T>.invoke(t: T) : Unit = accept(t)

public operator fun <T> Supplier<T>.invoke() : T = get()