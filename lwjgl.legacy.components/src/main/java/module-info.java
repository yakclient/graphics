import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.graphics.lwjgl.legacy.components.OpenGL2ComponentProvider;

module yakclient.graphics.lwjgl.legacy.components {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires yakclient.graphics.lwjgl.legacy;
    requires yakclient.graphics.components;
    requires lwjgl;

    provides DeferredComponentProvider with OpenGL2ComponentProvider;
}