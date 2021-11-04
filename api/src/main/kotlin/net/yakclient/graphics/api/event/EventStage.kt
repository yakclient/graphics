package net.yakclient.graphics.api.event

import java.util.function.Function

public fun interface EventStage : Function<EventData, EventData>

