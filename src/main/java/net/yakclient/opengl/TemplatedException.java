package net.yakclient.opengl;

import org.jetbrains.annotations.NotNull;

public abstract class TemplatedException extends RuntimeException {
    private static final String TEMPLATE_IDENTIFIER = "%s";

    @Override
    public final String getMessage() {
        return this.template();
    }

    protected final String fillTemplate(String template, String identifier, Object @NotNull ... values) {
        var last = template;
        for (var value : values) {
            last = last.replaceFirst(identifier, value.toString());
        }
        return last;
    }

    public String template() {
        return this.fillTemplate(this.getTemplate(), TEMPLATE_IDENTIFIER, this.getValues());
    }

    public abstract String getTemplate();

    public abstract Object[] getValues();
}
