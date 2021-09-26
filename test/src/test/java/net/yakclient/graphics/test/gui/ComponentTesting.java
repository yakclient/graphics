package net.yakclient.graphics.test.gui;

import net.yakclient.graphics.api.gui.GuiComponent;
import net.yakclient.graphics.api.gui.PropertyFactory;
import net.yakclient.graphics.api.render.RenderingContext;
import net.yakclient.graphics.components.Text;
import net.yakclient.graphics.test.OpenGLSetup;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL11;

public class ComponentTesting {
    public static void main(String[] args) {
        final GuiComponent component = new TestComponent();
        OpenGLSetup.setupAndStart(() -> {

        }, () -> {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


           final RenderingContext[] contexts = component.glRender(PropertyFactory.create().build());
            final RenderingContext[] other = new Text().glRender(PropertyFactory.create()
                    .addProp("value", "Wow!")
                    .addProp("x", 100D)
                    .addProp("y", 100D).build());
//            contexts[0].useRenderer().glRender();
            final var reversed = new RenderingContext[]{other[0], contexts[1]};
            for (RenderingContext context : contexts) {
                context.useRenderer().render();
            }
        });
    }

    @Test
    public void test() {
        System.out.println("hey");
    }
}
