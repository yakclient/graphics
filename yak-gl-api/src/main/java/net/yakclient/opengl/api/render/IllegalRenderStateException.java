package net.yakclient.opengl.api.render;


import net.yakclient.opengl.util.TemplatedException;

public class IllegalRenderStateException extends TemplatedException {
    private static final String ERROR_TEMPLATE = "Illegal rendering state: %s";
    private final String exception;

    public IllegalRenderStateException(String exception) {
        this.exception = exception;
    }

    @Override
    public String getTemplate() {
        return ERROR_TEMPLATE;
    }

    @Override
    public Object[] getValues() {
        return new String[]{this.exception};
    }
}
