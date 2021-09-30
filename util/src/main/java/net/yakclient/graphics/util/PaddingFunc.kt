package net.yakclient.graphics.util

public class PaddingFunc private constructor(
    public val paddingTop: Double,
    public val paddingBottom: Double,
    public val paddingRight: Double,
    public val paddingLeft: Double
) {
    public val paddingVertical: Double
        get() = paddingBottom + paddingTop
    public val paddingHorizontal: Double
        get() = paddingRight + paddingLeft

    public constructor(vertical: Double, horizontal: Double) : this(vertical, vertical, horizontal, horizontal)

    public constructor(all: Double) : this(all, all)
}