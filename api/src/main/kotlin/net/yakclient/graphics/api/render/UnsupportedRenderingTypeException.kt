package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.TemplatedException

public class UnsupportedRenderingTypeException(type: RenderingType, contextName: String) :
    TemplatedException("The rendering method '%s' you are attempting is unsupported by the context %s", type, contextName)