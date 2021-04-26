package net.yakclient.opengl.test.gui;

import net.yakclient.opengl.api.gui.GUIComponent;
import net.yakclient.opengl.api.gui.PropertyFactory;
import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.api.render.VerticeRenderingContext;
import net.yakclient.opengl.components.Text;
import net.yakclient.opengl.test.OpenGLSetup;
import org.lwjgl.opengl.GL11;

public class ComponentTesting {
    public static void main(String[] args) {
        final GUIComponent component = new TestComponent();
        OpenGLSetup.setupAndStart(() -> {

        }, () -> {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


           final GLRenderingContext[] contexts = component.glRender(PropertyFactory.create().build());
            final GLRenderingContext[] other = new Text().glRender(PropertyFactory.create()
                    .addProp("value", "Wow!")
                    .addProp("x", 100D)
                    .addProp("y", 100D).build());
//            contexts[0].useRenderer().glRender();
            final var reversed = new GLRenderingContext[]{other[0], contexts[1]};
            for (GLRenderingContext context : contexts) {
                context.useRenderer().glRender();
            }
        });
    }
}
