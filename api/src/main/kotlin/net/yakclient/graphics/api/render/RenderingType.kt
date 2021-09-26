package net.yakclient.graphics.api.render

/**
 * Defines a rendering type to use with OpenGL.
 *
 * @author Durgan McBroom
 */
enum class RenderingType {
    /**
     * This will be the default rendering type and should
     * be used by most applications. However in some cases it
     * does make sense to use VAOs instead.
     */
    VBO,

    /**
     * The VAO rendering type, mostly should go unused however
     * in some cases it may be faster than VBOs.
     */
    VAO,

    /**
     * Display lists should never be used and arent implemented
     * in some version of the yak-opengl-api.
     */
    DISPLAY_LIST,

    /**
     * For very basic tests immediate mode can be used however
     * is never recommended and like display lists might not be
     * implemented depending on the version you use of this api.
     */
    IMMEDIATE
}