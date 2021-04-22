package net.yakclient.opengl.test.gui;

import net.yakclient.opengl.api.gui.GUIComponent;
import net.yakclient.opengl.api.gui.PropertyFactory;
import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.test.OpenGLSetup;

public class ComponentTesting {
    public static void main(String[] args) {
        final GUIComponent component = new TestComponent();
        OpenGLSetup.setupAndStart(() -> {
        }, () -> {
           GLRenderingContext[] contexts = component.glRender(PropertyFactory.create().build());
            for (GLRenderingContext context : contexts) {
                context.useSuggestedRenderer().glRender();
            }
        });
    }
}
