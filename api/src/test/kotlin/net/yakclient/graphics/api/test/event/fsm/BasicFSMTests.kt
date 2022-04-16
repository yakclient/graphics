package net.yakclient.graphics.api.test.event.fsm

import net.yakclient.event.api.EventData
import net.yakclient.graphics.api.event.KeyboardActionData
import net.yakclient.graphics.api.event.MouseActionData
import net.yakclient.graphics.api.event.MouseMoveData
import net.yakclient.event.api.fsm.EventFSMScope
import net.yakclient.event.api.fsm.transitionsTo
import net.yakclient.event.api.fsm.with
import net.yakclient.event.api.fsm.withTime
import net.yakclient.graphics.api.test.event.TestEventOne
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import java.time.Duration
import java.time.Instant

class BasicFSMTests {
    @Test
    fun testBasicExample() {
        fun bounding(x: Int, y: Int, top: Int, left: Int, bottom: Int, right: Int): Boolean {
            return y in (top + 1) until bottom && left < x && right > x
        }

        //TODO https://github.com/evanw/fsm UI for generating this stuff dynamically from this
        // This whole example represents a mouse moving into a box, clicking, and then pressing a key which triggers a print statement.
        val fsm = EventFSMScope(true).apply {
            //STATES
            val initial = of("Starting state")
            val inBox = of("Mouse inside box")
            val clicked = of("Mouse clicked in box")
            val outsideNotClicked = of("Mouse outside box but not unfocused")

            // Variables - not really part of the example
            val x = 10
            val y = 10
            val w = 100
            val h = 100

            // Transitions between states.
            (initial transitionsTo inBox).with<MouseMoveData> {
                bounding(it.x, it.y, x, y, x + w, y + h)
            }

            (inBox transitionsTo initial).with<MouseMoveData> {
                !bounding(it.x, it.y, x, y, x + w, y + h)
            }

            (inBox transitionsTo clicked).with<MouseActionData> {
                it.key == 1 && it.state
            }

            (clicked transitionsTo inBox).with<MouseActionData> {
                it.key != 1 && it.state
            }

            (clicked transitionsTo clicked).with<KeyboardActionData> {
                println("Event happened")
                true
            }

            (clicked transitionsTo outsideNotClicked).with<MouseMoveData> {
                !bounding(it.x, it.y, x, y, x + w, y + h)
            }

            (outsideNotClicked transitionsTo clicked).with<MouseMoveData> {
                bounding(it.x, it.y, x, y, x + w, y + h)
            }

            (outsideNotClicked transitionsTo initial).with<MouseActionData> {
                it.state
            }
        }

        println(fsm.current.name)

        fsm.dispatch(MouseMoveData(50, 50, 11, 1))
        fsm.dispatch(MouseMoveData(500, 500, 11, 1))
        fsm.dispatch(MouseMoveData(60, 60, 11, 1))
        fsm.dispatch(MouseActionData(1, true))
        fsm.dispatch(MouseMoveData(500, 500, 11, 1))
        fsm.dispatch(KeyboardActionData(1, true))
        fsm.dispatch(MouseActionData(1, true))
    }

    @Test
    fun testWithTiming() {
        val fsm = EventFSMScope(true).apply {
            val initialTimed = timedOf("Initial")
            val second = of("First timed one")
            val lastOne = of("Last")

            (initialTimed transitionsTo second).withTime<KeyboardActionData> {
                println(Duration.between(it.instant, Instant.now()).toMillis())
                Duration.between(it.instant, Instant.now()).toMillis() <= 1000
            }

            (second transitionsTo lastOne).with<EventData> {
                println("Event")
                true
            }

            (second transitionsTo initialTimed).with<EventData> {
                false
            }
        }

        sleep(500)
        fsm.dispatch(KeyboardActionData(1, true))
        fsm.dispatch(TestEventOne())
        fsm.dispatch(TestEventOne())
    }
}