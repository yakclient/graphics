package net.yakclient.opengl.api.render;

import net.yakclient.opengl.util.TemplatedException;

public class UnsupportedRenderingTypeException extends TemplatedException {
    private static final String TEMPLATE = "The rendering method '%s' you are attempting is unsupported by the context %s";

    private final RenderingType type;
    private final String contextName;

    public UnsupportedRenderingTypeException(RenderingType type, String contextName) {
        this.type = type;
        this.contextName = contextName;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @Override
    public Object[] getValues() {
        return new Object[]{this.type, this.contextName};
    }
}
