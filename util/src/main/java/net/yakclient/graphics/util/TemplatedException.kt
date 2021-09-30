package net.yakclient.graphics.util

public abstract class TemplatedException(
    private val template: String,
    private val values: List<Any>,
) : IllegalStateException() {
    public constructor(template: String, vararg values: Any) : this(template, listOf(values))

    override val message: String
        get() = values.fold(template) { acc, value -> acc.replaceFirst(TEMPLATE_IDENTIFIER, value.toString()) }

    public companion object {
        private const val TEMPLATE_IDENTIFIER: String = "%s"
    }
}