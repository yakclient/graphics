import net.yakclient.event.api.EventDispatcher;
import net.yakclient.graphics.lwjgl.event.GL3KeyboardActionDispatcher;
import net.yakclient.graphics.lwjgl.event.GL3MouesButtonDispatcher;
import net.yakclient.graphics.lwjgl.event.GL3MouseMoveDispatcher;

module yakclient.graphics.lwjgl {
    requires kotlin.stdlib;
    requires transitive yakclient.graphics.api;
    requires org.lwjgl.opengl;
    requires org.lwjgl.glfw;
    requires yakclient.graphics.lwjgl.util;

    exports net.yakclient.graphics.lwjgl.render;

    provides EventDispatcher with GL3KeyboardActionDispatcher,GL3MouesButtonDispatcher,GL3MouseMoveDispatcher;
}