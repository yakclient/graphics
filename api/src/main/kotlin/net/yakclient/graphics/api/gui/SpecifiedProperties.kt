package net.yakclient.graphics.api.gui

import java.util.Optional

/**
 * Defines pre-determined properties that given GUIProperties
 * should have.
 *
 * @author Durgan McBroom
 */
abstract class SpecifiedProperties(private val properties: GuiProperties) {
    protected operator fun <T> get(name: String): Optional<T> {
        return Optional.ofNullable(properties.get(name))
    }
}