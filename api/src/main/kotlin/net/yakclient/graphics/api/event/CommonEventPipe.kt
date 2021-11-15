package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.EventStage

public class CommonEventPipe private constructor(
    private val stages: MutableList<EventStage> = ArrayList()
) : EventPipeline(stages) {
    private val subscribedEvents: MutableSet<String> = HashSet()

    public companion object {
        @JvmStatic
        public val commonPipe: CommonEventPipe = CommonEventPipe()
    }

    public fun add(stage: EventStage): Unit = let { stages.add(stage) }

    public fun <T : EventData> subscribeTo(dispatcher: Class<out EventDispatcher<T>>) : Unit = if (subscribedEvents.add(dispatcher.name)) EventDispatchManager.load(dispatcher).subscribe(CommonPipeEventHook()) else Unit

    private inner class CommonPipeEventHook<T : EventData> : EventHook<T> {
        override fun accept(t: T) = Unit.apply { this@CommonEventPipe.dispatch(t) }
    }
}