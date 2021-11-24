package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData

public interface EventState {
    public val exits: List<Transition>
    public val name: String

    public fun accept() {}

    public fun <T : EventData> accept(event: T)

    public companion object {
//        @JvmField
//        public val DEFAULT_NAME: String = "unnamed@"

        private var id: Int = -1

        @JvmStatic
        public fun defaultName() : String = "unnamed@${id++}"
    }
}


//public open class DelegatedEventState(
//    override val exits: List<Transition<*>>,
////    private val matcher: TransitionDelegator<EventData> = MatchByType(),
//    name: String? = null
//) : TransitioningState {
//    public override val name: String = name ?: "unnamed@${System.identityHashCode(this)}"
//    override fun accept() {
//        TODO("Not yet implemented")
//    }
//
//    override fun <T : EventData> accept(event: T) {
//        TODO("Not yet implemented")
//    }
//
//    public fun <T : EventData> dispatch(data: T): Unit = matcher.accept(data, exits)
//}

//public class MutableEventState(
//    private val exits: MutableList<Transition<*>> = ArrayList(),
//    matcher: TransitionMatcher = BY_TYPE,
//    name: String? = null
//) : EventState(exits, matcher, name), MutableList<Transition<*>> by exits

