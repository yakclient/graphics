package net.yakclient.opengl.util;

public class IllegalVerticeOperationException extends TemplatedException {
    private static final String ERROR_TEMPLATE = "This is not a valid operation! Exception: '%s'";
    private final String attempt;

    public IllegalVerticeOperationException(String attempt) {
        this.attempt = attempt;
    }

    @Override
    public String getTemplate() {
        return ERROR_TEMPLATE;
    }

    @Override
    public Object[] getValues() {
        return new String[]{this.attempt};
    }
}
