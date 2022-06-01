package net.yakclient.graphics.lwjgl.components

import net.yakclient.graphics.api.GuiPropertiesMap
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.lwjgl.render.GLRenderingData
import net.yakclient.graphics.lwjgl.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.buffer.safeFloatBufOf
import org.lwjgl.opengl.GL11

public class OpenGL3Text : Text() {
    override fun renderNatively(props: GuiPropertiesMap): List<RenderingContext> {
        val value = props.requireAs<String>("value")
        val font: TextureFont = props.getAs<TextureFont>("font") ?: FontFactory.fontOf()
        val color: ColorFunction = props.getAs("backgroundColor") ?: ColorCodes.WHITE.toColorFunc()

        var x = props.requireAs<Float>("x")
        val y = props.requireAs<Float>("y")

        return value.map {
            val data = checkNotNull(font[it]) { "Failed to find character: $it" }
            val tex = data.backingTexture

            val vertices = safeFloatBufOf(8)
                .put(x).put(y)
                .put(x + data.width).put(y)
                .put(x + data.width).put(y + data.height)
                .put(x).put(y + data.height)


            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    vertices = vertices,
                    verticeStride = 2,
                    colors = color.toAggregation(vertices, 2),
                    colorStride = 4,
                    texs = tex.texToBuf(),
                    texStride = 2,
                    texture = data.backingTexture
                )
            ).also {
                x += data.width
            }
        }
    }
}