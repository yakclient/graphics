package net.yakclient.opengl.render;

import net.yakclient.opengl.OpenGLSetup;
import net.yakclient.opengl.util.ColorAggregation;
import net.yakclient.opengl.util.VerticeAggregation;
import org.lwjgl.opengl.GL11;

public class RendererTests {
    public static void main(String[] args) {
        final GLRenderingContext context = testVAOContext();

        OpenGLSetup.setupAndStart(()-> {
           RenderMethod.VAO.type(context).glRender();
        });
    }

    private static GLRenderingContext testVAOContext() {
        final VerticeAggregation collection = new VerticeAggregation()
                .add(10,10, 0)
                .add(10,100, 0)
                .add(100,10, 0);
        final ColorAggregation colors = new ColorAggregation()
                .add(1, 0, 0)
                .add(0, 1, 0)
                .add(0, 0, 1);

        return new GLRenderingContext.ContextBuilder(GL11.GL_TRIANGLES, GLRenderingData.create().addVertices(collection).addColors(colors).build()).build();
    }
}
