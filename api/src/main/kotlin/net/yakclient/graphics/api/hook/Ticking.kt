package net.yakclient.graphics.api.hook

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

//public abstract class TickingHookSubscriber<T: HookData> : HookSubscriber<T>(), Ticking {
//    override fun hook() : Unit = HookTickManager.register(this)
//}

public fun interface Ticking {
    public fun tick()
}

public object HookTickManager {
    private val toTick: Queue<Ticking> = ConcurrentLinkedQueue()
//    private val theTicker: Thread = Thread {
//        while (true) {
//            Thread.sleep(tickRate)
//            toTick.forEach { it.tick() }
//        }
//    }
//    @Volatile
//    public var shouldTick : Boolean = true
//    public var tickRate: Long = 50
//
//    init {
//        theTicker.run {
//            isDaemon = true
//            name = "Hook Ticker"
//        }
//    }

    public fun tickThem() : Unit = toTick.forEach { it.tick() }

    public fun register(ticker: Ticking) {
        toTick.add(ticker)
    }
}