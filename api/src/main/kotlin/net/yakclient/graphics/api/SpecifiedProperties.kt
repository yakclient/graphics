package net.yakclient.graphics.api

import java.util.Optional

/**
 * Defines pre-determined properties that given GUIProperties
 * should have.
 *
 * @author Durgan McBroom
 */
public abstract class SpecifiedProperties(private val properties: GuiProperties) {
    protected operator fun <T> get(name: String): Optional<T> {
        return Optional.ofNullable<T>(properties.getAs(name))
    }
}