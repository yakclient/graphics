import net.yakclient.graphics.api.hook.KeyboardActionSubscriber;
import net.yakclient.graphics.api.hook.MouseButtonEventSubscriber;
import net.yakclient.graphics.api.hook.MouseMoveSubscriber;
import net.yakclient.graphics.opengl2.event.GL2KeyboardActionSubscriber;
import net.yakclient.graphics.opengl2.event.GL2MouseButtonEventSubscriber;
import net.yakclient.graphics.opengl2.event.GL2MouseMoveSubscriber;

module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires java.base;
    requires io.github.emilyydev.asp;

    uses MouseMoveSubscriber;
    uses MouseButtonEventSubscriber;
    uses KeyboardActionSubscriber;
    provides MouseMoveSubscriber with GL2MouseMoveSubscriber;
    provides MouseButtonEventSubscriber with GL2MouseButtonEventSubscriber;
    provides KeyboardActionSubscriber with GL2KeyboardActionSubscriber;

    exports net.yakclient.graphics.opengl2.render;
}