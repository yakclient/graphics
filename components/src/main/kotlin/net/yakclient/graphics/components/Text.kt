package net.yakclient.graphics.components

import net.yakclient.graphics.api.DeferredComponent

public open class Text : DeferredComponent() {
//    private val charAddition = 10

//    override fun renderNatively(props: GuiProperties): List<RenderingContext> {
//        val loader = ServiceLoader.load(Text::class.java)
//        val text = loader.firstOrNull() ?: throw IllegalStateException("No Text component implementations on the classpath!")
//
//        return text.renderNatively(props)
//        val value = props.requireAs<String>("value")
//        val font = props.getAs<YakFont>("font") ?: YakFontFactory.fontOf()
//
//        var x = props.requireAs<Int>("x")
//        val y = props.requireAs<Int>("y")
//
//        return value.map {
//            val data = checkNotNull(font[it]) { "Failed to find character: $it" }
//
////            val width = data.width
////            val height = data.height
////
//////            val cx = data.storedX
//////            val cy = data.storedY
//////            val rx = 1f / (width / cx)
//////            val ry = 1f / (height / cy)
//////            val rw = 1f / (width / data.width)
//////            val rh = 1f / (height / data.height)
//
//            VerticeRenderingContext(
//                GL11.GL_QUADS,
//                GL11.GL_TEXTURE_2D,
//                GLRenderingData(
//                    verticesOf(
//                        Vertice(x, y),
//                        Vertice(x + data.width, y),
//                        Vertice(x, y + data.height),
//                        Vertice(x + data.width, y + data.height),
//                    ),
//                    texs = texsOf(
//                        TexNode(0f, 0f),
//                        TexNode(1f, 0f),
//                        TexNode(0f, 1f),
//                        TexNode(1f, 1f),
//                    ),
//                    texture = data.backingTexture
//                )
//            ).also {
//                x += data.width
//            }
        }
//        return value.map {
//            object : RenderingContext {
//                override fun useRenderer(type: RenderingType): Renderer<RenderingContext> {
//                    return object : Renderer<VerticeRenderingContext> {
//                        override val context: VerticeRenderingContext
//                            get() = Verti
//
//                        override fun render() {
//
//                        }
//                    }
//                }
//            }
//        }
//        for (c in value) {
//
//        }
//    }
//}

//public fun Font(): Component = { props ->
//    val value = props.requireAs<String>("value")
//    val font = props.requireAs<YakGLFont>("font")
//
//    font.fontTex.bind()
//
////        value.for
//    for (c in value) {
//        build(use<Box>(0)) {
//            val char = checkNotNull(font.characters[c.code]) { "Failed to find character: $c" }
//            set("x") to char.storedX
//            set("y") to char.storedY
//            set("width") to char.width
//            set("height") to char.height
//            set("backgroundimage") to font.fontTex
//        }
//    }
//}