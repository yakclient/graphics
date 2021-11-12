package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData
import java.util.function.Function

public fun interface EventStage : Function<EventData, EventData>

