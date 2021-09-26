package net.yakclient.graphics.api.gui

import net.yakclient.graphics.util.TemplatedException

private const val ERROR_TEMPLATE = "Your GUI Properties are mis-configured! Requested Prop: '%s', Extra: '%s'"

public class IllegalPropertyException @JvmOverloads constructor(
    private val property: String,
    private val extra: String = "NONE"
) : TemplatedException() {
    override fun getTemplate(): String {
        return ERROR_TEMPLATE
    }

    override fun getValues(): Array<Any> {
        return arrayOf(property, extra)
    }
}

public fun illegalProperty(name: String, extra: String = "NONE") : IllegalPropertyException = IllegalPropertyException(name, extra)