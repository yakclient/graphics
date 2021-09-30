package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.TemplatedException

public class IllegalRenderStateException(exception: String) : TemplatedException("Illegal rendering state: %s", exception)
