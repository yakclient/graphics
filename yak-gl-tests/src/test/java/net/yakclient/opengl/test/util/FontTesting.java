package net.yakclient.opengl.test.util;


import net.yakclient.opengl.api.gui.PropertyFactory;
import net.yakclient.opengl.components.Text;
import net.yakclient.opengl.test.OpenGLSetup;
import org.lwjgl.opengl.GL11;

public class FontTesting {
    public static void main(String[] args) {
        OpenGLSetup.setupAndStart(() -> {

        }, () -> {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            new Text().glRender(PropertyFactory.create().addProp("value", "Wow!").addProp("x", 100D).addProp("y", 100D).build())[0].useRenderer().glRender();
//            new FontRenderingContext( YakFontFactory.create("Default", 24).buildFont(), "Make me!", ColorCodes.WHITE, 100, 100).useRenderer().glRender();;

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            YakFontFactory.create("Default", 24).buildFont().drawString("Hey Testing!", 100, 100);
        });
    }
}
