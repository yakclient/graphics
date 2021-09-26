package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.TemplatedException

public class IllegalRenderStateException(private val exception: String) : TemplatedException() {
    override fun getTemplate(): String {
        return ERROR_TEMPLATE
    }

    override fun getValues(): Array<Any> {
        return arrayOf<String>(exception)
    }

    public companion object {
        private const val ERROR_TEMPLATE = "Illegal rendering state: %s"
    }
}