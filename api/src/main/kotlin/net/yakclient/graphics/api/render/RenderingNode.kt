package net.yakclient.graphics.api.render

//public open class RenderingNode(
//    public open val context: List<RenderingContext>,
//    public open val children: List<RenderingNode>
//) {
//    public fun preRender(forceRender: Boolean = false) : List<RenderingContext> {
//
//        if (forceRender || context.any { it.needsReRender }) {
//            return
//        }
//    }
//}
//
//public class MutableRenderingNode(
//    override val context: MutableList<RenderingContext> = ArrayList(),
//    override val children: MutableList<RenderingNode> = ArrayList()
//) : RenderingNode(context, children)
