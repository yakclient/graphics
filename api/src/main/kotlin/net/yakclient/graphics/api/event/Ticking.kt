package net.yakclient.graphics.api.event

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

public fun interface Ticking {
    public fun tick()
}

public object TickManager {
    private val toTick: Queue<Ticking> = ConcurrentLinkedQueue()

    public fun tickThem() : Unit = toTick.forEach { it.tick() }

    public fun register(ticker: Ticking) {
        toTick.add(ticker)
    }
}