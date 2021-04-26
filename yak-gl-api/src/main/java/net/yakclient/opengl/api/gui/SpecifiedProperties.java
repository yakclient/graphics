package net.yakclient.opengl.api.gui;

import java.util.Optional;

/**
 * Defines pre-determined properties that given GUIProperties
 * should have.
 *
 * @author Durgan McBroom
 */
public abstract class SpecifiedProperties {
    private final GUIProperties properties;

    public SpecifiedProperties(GUIProperties properties) {
        this.properties = properties;
    }

    protected <T> Optional<T> get(String name) {
        return Optional.ofNullable(this.properties.get(name));
    }
}
