package net.yakclient.graphics.components

import net.yakclient.graphics.api.DeferredComponent

/**
 * A `Box` represents the closest thing that yak-opengl-api
 * has to the HTML Div tag. It is a box that has all of the same events
 * as a Div and has some(but not all) of the same styling properties.
 *
 *
 * Note: This class will not wrap children; grow with children; define its
 * top left as its children's origins etc. It purely represents a static box
 * defined by its required properties.
 *
 * @author Durgan McBroom
 * @since 1.0
 */
public open class Box : DeferredComponent()