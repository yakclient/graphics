package net.yakclient.opengl.gui;

import net.yakclient.opengl.OpenGLSetup;

public class ComponentTesting {
    public static void main(String[] args) {
        final GUIComponent component = new TestComponent();
        OpenGLSetup.setupAndStart(() -> {
        }, () -> {
            component.glRender(PropertyFactory.create().build());

        });
    }
}
