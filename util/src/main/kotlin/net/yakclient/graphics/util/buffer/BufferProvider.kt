package net.yakclient.graphics.util.buffer

/**
 * Provides platform specific implementations of Buffer type B.
 *
 * @see SafeBuffer
 */
public interface BufferProvider<B: SafeBuffer<*, *>> {
    /**
     * Creates a new SafeBuffer of type B based on the input size
     * given. Exceeding this size is expected to always throw an
     * exception.
     *
     * @param size The size of a buffer to create.
     * @return The buffer of type B that was created.
     */
    public fun createNew(size: Int) : B
}