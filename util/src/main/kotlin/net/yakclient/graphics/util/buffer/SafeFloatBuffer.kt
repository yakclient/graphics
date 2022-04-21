package net.yakclient.graphics.util.buffer

import java.nio.FloatBuffer
import java.util.*

@Suppress("UNCHECKED_CAST")
private val provider: BufferProvider<SafeFloatBuffer> =
    ServiceLoader.load(SafeFloatBufferProvider::class.java).firstOrNull() as? BufferProvider<SafeFloatBuffer>
        ?: throw IllegalStateException("Failed to load a provider for the safe float buffer! Make sure the providing module is on the classpath.")

public interface SafeFloatBufferProvider : BufferProvider<SafeFloatBuffer>

public typealias SafeFloatBuffer = SafeBuffer<FloatBuffer, Float>

public fun safeFloatBufOf(): SafeFloatBuffer = provider.createNew(0)

public fun safeFloatBufOf(size: Int): SafeFloatBuffer = provider.createNew(size)

public fun safeFloatBufOf(floats: Array<Float>): SafeFloatBuffer = safeFloatBufOf(floats.size).putAll(floats)

public fun safeFloatBufOf(floats: FloatArray): SafeFloatBuffer = safeFloatBufOf(Array(floats.size) { floats[it] })

