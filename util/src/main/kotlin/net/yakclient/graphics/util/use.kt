package net.yakclient.graphics.util

import java.io.Closeable
import kotlin.io.use

public inline fun <T : AutoCloseable, R> T.use(block: (T) -> R): R =
    AutoCloseableWrapper(this).use { block(it.closeable) }

public class AutoCloseableWrapper<T : AutoCloseable>(
    public val closeable: T
) : Closeable {
    override fun close() {
        closeable.close()
    }
}
