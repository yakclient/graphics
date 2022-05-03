package net.yakclient.graphics.util.buffer

import java.io.Closeable
import java.nio.Buffer

public interface SafeBuffer<B : Buffer, N : Number> : Closeable, Iterable<N> {
    public val buffer: B
    public val size: Int

    public fun put(number: N): SafeBuffer<B, N>

    public fun putAll(numbers: Array<N>): SafeBuffer<B, N>

    public fun putAll(buf: SafeBuffer<B, N>) : SafeBuffer<B, N>

    public fun get(index: Int): N

    public fun get(start: Int, end: Int): Array<N>

    override fun iterator(): Iterator<N> {
        return object : Iterator<N> {
            private var i = 0

            override fun hasNext(): Boolean = i < size

            override fun next(): N = get(i++)
        }
    }
}