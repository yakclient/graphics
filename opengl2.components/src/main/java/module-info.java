import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.graphics.opengl2.components.OpenGL2ComponentProvider;

module yakclient.graphics.open2gl.components {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires yakclient.graphics.open2gl;
    requires yakclient.graphics.components;
    requires lwjgl;
    requires io.github.emilyydev.asp;


    uses DeferredComponentProvider;
    provides DeferredComponentProvider with OpenGL2ComponentProvider;
}