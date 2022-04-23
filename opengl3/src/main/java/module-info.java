import net.yakclient.event.api.EventDispatcher;
import net.yakclient.graphics.api.event.KeyboardActionDispatcher;
import net.yakclient.graphics.api.event.MouseButtonEventDispatcher;
import net.yakclient.graphics.api.event.MouseMoveDispatcher;
import net.yakclient.graphics.opengl3.event.GL3KeyboardActionDispatcher;
import net.yakclient.graphics.opengl3.event.GL3MouesButtonDispatcher;
import net.yakclient.graphics.opengl3.event.GL3MouseMoveDispatcher;

module yakclient.graphics.opengl3 {
    requires kotlin.stdlib;
    requires transitive yakclient.graphics.api;
    requires org.lwjgl.opengl;
    requires org.lwjgl.glfw;

    exports net.yakclient.graphics.opengl3.render;

    provides EventDispatcher with GL3KeyboardActionDispatcher,GL3MouesButtonDispatcher,GL3MouseMoveDispatcher;
//    provides KeyboardActionDispatcher with GL3KeyboardActionDispatcher;
//    provides MouseButtonEventDispatcher with GL3MouesButtonDispatcher;
//    provides MouseMoveDispatcher with GL3MouseMoveDispatcher;
}