package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.lang.ref.SoftReference
import kotlin.properties.Delegates
import java.util.logging.*

public open class EventFSM(
    first: EventState,
    private val debug: Boolean = false
) {
    private val logger = Logger.getLogger("net.yakclient.graphics.api.event.fsm")
    internal var current: EventState by Delegates.observable(first) { _, old, new ->
        new.accept()
        if (debug) logger.log(Level.INFO, "State changed from ${old.name} to ${new.name}")
    }

    public fun dispatch(t: EventData) {
        current.accept(t)
    }
}

public class FSMReference(
    fsm: EventFSM
) : SoftReference<EventFSM>(fsm) {
    @Synchronized
    public fun set(state: EventState): Unit = let { get()?.current = state }
}