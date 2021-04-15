package net.yakclient.opengl.gui;

import net.yakclient.opengl.OpenGLSetup;
import net.yakclient.opengl.render.GLRenderingContext;

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
