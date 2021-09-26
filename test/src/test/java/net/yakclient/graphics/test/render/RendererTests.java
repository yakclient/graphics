package net.yakclient.graphics.test.render;

import net.yakclient.graphics.api.render.VerticeRenderingContext;
import net.yakclient.graphics.api.render.GLRenderingData;
import net.yakclient.graphics.test.OpenGLSetup;
import net.yakclient.graphics.util.ColorAggregation;
import net.yakclient.graphics.util.VerticeAggregation;
import org.lwjgl.opengl.GL11;

public class RendererTests {
    public static void main(String[] args) {
        final VerticeRenderingContext context = testVAOContext();

        OpenGLSetup.setupAndStart(()-> {
//           RenderingType.VAO.type(context).glRender();
        });
    }

    private static VerticeRenderingContext testVAOContext() {
        final VerticeAggregation collection = new VerticeAggregation()
                .add(10,10, 0)
                .add(10,100, 0)
                .add(100,10, 0);
        final ColorAggregation colors = new ColorAggregation()
                .add(1, 0, 0)
                .add(0, 1, 0)
                .add(0, 0, 1);

        return new VerticeRenderingContext.VerticeContextBuilder(GL11.GL_TRIANGLES, GLRenderingData.create().addVertices(collection).addColors(colors).build()).build();
    }
}
