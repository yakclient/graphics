package net.yakclient.graphics.util

import net.yakclient.common.util.immutableLateInit

public interface ScreenAccess {
    public val width: Int
    public val height: Int

    public companion object {
        @JvmStatic
        public var access: ScreenAccess by immutableLateInit()
    }
}