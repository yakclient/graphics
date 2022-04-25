package net.yakclient.graphics.lwjgl.legacy.components

import net.yakclient.graphics.api.GuiPropertiesMap
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.lwjgl.legacy.render.GLRenderingData
import net.yakclient.graphics.lwjgl.legacy.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.buffer.safeFloatBufOf
import org.lwjgl.opengl.GL11

public class OpenGL2Text : Text() {
    override fun renderNatively(props: GuiPropertiesMap): List<RenderingContext> {
        val value = props.requireAs<String>("value")
        val font = props.getAs<YakFont>("font") ?: YakFontFactory.fontOf()

        var x = props.requireAs<Float>("x")
        val y = props.requireAs<Float>("y")

        return value.map {
            val data = checkNotNull(font[it]) { "Failed to find character: $it" }

            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    vertices = safeFloatBufOf(8)
                        .put(x).put(y)
                        .put(x + data.width).put(y)
                        .put(x + data.width).put(y + data.height)
                        .put(x).put(y + data.height),
                    verticeSize = 2,
                    texs = safeFloatBufOf(8)
                        .put(0f).put(0f)
                        .put(1f).put(0f)
                        .put(1f).put(1f)
                        .put(0f).put(1f),
                    texSize = 2,
                    texture = data.backingTexture
                )
            ).also {
                x += data.width
            }
        }
    }
}