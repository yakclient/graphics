package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData

public fun interface TransitionEvaluator : (EventData, List<Transition>) -> Unit

public class TransitionEvaluatingEventState(
    override val name: String,
    override val exits: List<Transition>,
    private val evaluator: TransitionEvaluator
) : EventState {
    override fun <T : EventData> accept(event: T): Unit = evaluator(event, exits)
}

@JvmOverloads
public fun transitionEvaluator(
    name: String = EventState.defaultName(),
    exits: List<Transition> = ArrayList(),
    evaluator: TransitionEvaluator
): TransitionEvaluatingEventState = TransitionEvaluatingEventState(name, exits, evaluator)

