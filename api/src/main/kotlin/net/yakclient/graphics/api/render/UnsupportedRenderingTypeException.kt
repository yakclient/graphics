package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.TemplatedException

class UnsupportedRenderingTypeException(private val type: RenderingType, private val contextName: String) :
    TemplatedException() {
    override fun getTemplate(): String {
        return TEMPLATE
    }

    override fun getValues(): Array<Any> {
        return arrayOf(type, contextName)
    }

    companion object {
        private const val TEMPLATE = "The rendering method '%s' you are attempting is unsupported by the context %s"
    }
}