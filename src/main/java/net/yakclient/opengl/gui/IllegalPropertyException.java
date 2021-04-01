package net.yakclient.opengl.gui;

import net.yakclient.opengl.TemplatedException;

public class IllegalPropertyException extends TemplatedException {
    private static final String ERROR_TEMPLATE = "Your GUI Properties are mis-configured! Requested Prop: '%s', Extra: '%s'";

    private final String property;
    private final String extra;

    public IllegalPropertyException(String property) {
        this.property = property;
        this.extra = "NONE";
    }

    public IllegalPropertyException(String property, String extra) {
        this.property = property;
        this.extra = extra;
    }

    @Override
    public String getTemplate() {
        return ERROR_TEMPLATE;
    }

    @Override
    public Object[] getValues() {
        return new String[]{this.property, extra};
    }
}
