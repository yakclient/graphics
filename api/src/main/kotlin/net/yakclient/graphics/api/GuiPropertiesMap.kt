package net.yakclient.graphics.api

import java.util.*

public interface GuiProperties {
    public fun <T> getAs(key: String): T?

    public fun <T> optionallyAs(key: String): Optional<T> = Optional.ofNullable(getAs(key))

    public fun <T> requireAs(key: String): T
}

public data class GuiPropertiesMap(
    private val delegate: Map<String, Any?>,
) : GuiProperties, Map<String, Any?> by delegate {
    override fun <T> getAs(key: String): T? = this[key] as T

    override fun <T> optionallyAs(key: String): Optional<T> = Optional.ofNullable(getAs(key))

    override fun <T> requireAs(key: String): T =
        if (!containsKey(key)) throw IllegalPropertyException(key) else getAs(key)!!
}

public const val CHILD_NAME: String = "children"

public data class MutableGuiPropertiesMap(
    private val _delegate: MutableMap<String, Any?> = HashMap()
) : MutableMap<String, Any?> by _delegate {
    init {
        _delegate[CHILD_NAME] = ArrayList<ComponentRenderingContext<*>>()
    }

    override fun put(key: String, value: Any?): Any? {
        if (
            key.equals(CHILD_NAME, ignoreCase = true)
        ) throw IllegalPropertyException(key, "The name $CHILD_NAME is reserved for children of components")

        _delegate[key.lowercase()] = value

        return value
    }

    public fun addChild(component: ComponentRenderingContext<*>) {
        (this[CHILD_NAME] as MutableList<ComponentRenderingContext<*>>).add(component)
    }

    public fun build(): GuiPropertiesMap {
        return GuiPropertiesMap(this)
    }
}

