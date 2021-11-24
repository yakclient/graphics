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
    public var current: EventState by Delegates.observable(first) { _, old, new ->
        new.accept()
        if (debug) logger.log(Level.INFO, "State changed from ${old.name} to ${new.name}")
    }

    public fun dispatch(t: EventData) {
        if (debug) logger.log(Level.INFO, "Received an event of type : ${t::class.java}")

        current.accept(t)
    }
}

public class FSMReference(
    fsm: EventFSM
) : SoftReference<EventFSM>(fsm) {
    public fun set(state: EventState): Unit = let { get()?.current = state }
}