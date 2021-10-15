package net.yakclient.graphics.opengl2.components

import net.yakclient.graphics.api.gui.GuiProperties
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.opengl2.render.GLRenderingData
import net.yakclient.graphics.opengl2.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import org.lwjgl.opengl.GL11

public class OpenGL2Text : Text() {
    override fun renderNatively(props: GuiProperties): List<RenderingContext> {
        val value = props.requireAs<String>("value")
        val font = props.getAs<YakFont>("font") ?: YakFontFactory.fontOf()

        var x = props.requireAs<Int>("x")
        val y = props.requireAs<Int>("y")

        return value.map {
            val data = checkNotNull(font[it]) { "Failed to find character: $it" }

            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    verticesOf(
                        Vertice(x, y),
                        Vertice(x + data.width, y),
                        Vertice(x, y + data.height),
                        Vertice(x + data.width, y + data.height),
                    ),
                    texs = texsOf(
                        TexNode(0f, 0f),
                        TexNode(1f, 0f),
                        TexNode(0f, 1f),
                        TexNode(1f, 1f),
                    ),
                    texture = data.backingTexture
                )
            ).also {
                x += data.width
            }
        }
    }
}