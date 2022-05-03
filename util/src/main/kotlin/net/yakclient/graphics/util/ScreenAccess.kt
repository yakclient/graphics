package net.yakclient.graphics.util

import java.util.ServiceLoader

public interface ScreenAccess {
    public val width: Int
    public val height: Int

    public companion object {
        public val access: ScreenAccess = ServiceLoader.load(ScreenAccess::class.java).first()
    }
}