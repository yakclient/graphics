import net.yakclient.graphics.components.Box;
import net.yakclient.graphics.components.Text;
import net.yakclient.graphics.opengl2.components.OpenGL2Box;
import net.yakclient.graphics.opengl2.components.OpenGL2Text;

module yakclient.graphics.open2gl.components {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires yakclient.graphics.open2gl;

    provides Box with OpenGL2Box;
    provides Text with OpenGL2Text;
}