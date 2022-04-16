import net.yakclient.graphics.api.event.KeyboardActionDispatcher;
import net.yakclient.graphics.api.event.MouseButtonEventDispatcher;
import net.yakclient.graphics.api.event.MouseMoveDispatcher;
import net.yakclient.graphics.opengl2.event.*;

module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires transitive yakclient.graphics.api;
    requires java.base;
    requires lwjgl;

    provides MouseMoveDispatcher with GL2MouseMoveDispatcher;
    provides MouseButtonEventDispatcher with GL2MouseButtonEventDispatcher;
    provides KeyboardActionDispatcher with GL2KeyboardActionDispatcher;

    exports net.yakclient.graphics.opengl2.render;
}