package net.yakclient.graphics.api.gui

import net.yakclient.graphics.util.TemplatedException

public class IllegalPropertyException @JvmOverloads constructor(
    property: String,
    extra: String = "NONE"
) : TemplatedException("Your GUI Properties are mis-configured! Requested Prop: '%s', Extra: '%s'", listOf(property, extra))

public fun illegalProperty(name: String, extra: String = "NONE") : IllegalPropertyException = IllegalPropertyException(name, extra)