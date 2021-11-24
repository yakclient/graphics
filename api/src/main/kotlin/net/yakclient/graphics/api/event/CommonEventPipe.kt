package net.yakclient.graphics.api.event

public class CommonEventPipe private constructor(): EventPipeline.MutablePipeline() {
    private val subscribedEvents: MutableSet<String> = HashSet()

    public companion object {
        @JvmStatic
        public val commonPipe: CommonEventPipe = CommonEventPipe()
    }

    public fun <T : EventData> subscribeTo(dispatcher: Class<out EventDispatcher<T>>) : Unit = if (subscribedEvents.add(dispatcher.name)) EventDispatchManager.load(dispatcher).subscribe(CommonPipeEventHook()) else Unit

    private inner class CommonPipeEventHook<T : EventData> : EventHook<T> {
        override fun accept(t: T) = Unit.apply { this@CommonEventPipe.dispatch(t) }
    }
}