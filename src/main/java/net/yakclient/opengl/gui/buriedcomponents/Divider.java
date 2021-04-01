package net.yakclient.opengl.gui.buriedcomponents;

import net.yakclient.opengl.gui.BuriedGUIComponent;
import net.yakclient.opengl.gui.GUIProperties;
import net.yakclient.opengl.util.VerticeCollection;
import net.yakclient.opengl.util.VerticeCollection2;
import net.yakclient.opengl.util.YakGLUtils;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class Divider extends BuriedGUIComponent {
    @Override
    public void glRender(GUIProperties properties) {

        final double width = properties.get("width");
        final double height = properties.get("height");
        final double x = properties.get("x");
        final double y = properties.get("y");

        final VerticeCollection2 collection = new VerticeCollection2()
                .addVertice(x, y)
                .addVertice(x + width, y)
                .addVertice(x + width, y + height)
                .addVertice(x, y + height);

        final VerticeCollection2 colorsDefault = new VerticeCollection2()
                .addColor3f(1,0,0)
                .addColor3f(0,1,0)
                .addColor3f(0,0,1)
                .addColor3f(1,1,1);

        final VerticeCollection2 colorsHover = new VerticeCollection2()
                .addColor3f(1,1,1)
                .addColor3f(1,0,0)
                .addColor3f(0,1,0)
                .addColor3f(0,0,1);


        if (this.rectBounding(Mouse.getX(), Mouse.getY(), y, x, x + width, y + height)) collection.combine(colorsHover);
        else collection.combine(colorsDefault);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glVertexPointer(2, 0, YakGLUtils.flipBuf(collection.verticesAsBuffer()));
        glColorPointer(4, 0, YakGLUtils.flipBuf(collection.colorsAsBuffer()));

        glDrawArrays(GL_QUADS, 0, 4);

        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        this.renderChildren(properties);
    }

    private boolean rectBounding(double x, double y, double top, double left, double bottom, double right) {
        return (top < y && bottom > y) && (left < x && right > x);
    }
}
