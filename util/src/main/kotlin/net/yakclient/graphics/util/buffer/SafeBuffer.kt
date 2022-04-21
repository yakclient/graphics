package net.yakclient.graphics.util.buffer

import java.io.Closeable
import java.nio.Buffer

public interface SafeBuffer<out B : Buffer, N : Number> : Closeable {
    public val buffer: B
    public val size: Int

    public fun put(number: N): SafeBuffer<B, N>

    public fun putAll(numbers: Array<N>): SafeBuffer<B, N>
}