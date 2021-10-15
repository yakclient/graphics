package net.yakclient.graphics.api.gui

import java.util.*

//make this a data class so that properties can be compared.
public data class GuiProperties(
    private val delegate: Map<String, Any?>,
) : Map<String, Any?> by delegate {
    public fun <T> getAs(key: String): T? = this[key] as T

    public fun <T> optionallyAs(key: String): Optional<T> = Optional.ofNullable(getAs(key))

    public fun <T> requireAs(key: String): T =
        if (!containsKey(key)) throw IllegalPropertyException(key) else getAs(key)!!

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GuiProperties

        if (delegate != other.delegate) return false

        return true
    }

    override fun hashCode(): Int {
        return delegate.hashCode()
    }
}

public const val CHILD_NAME: String = "children"

public class PropertyFactory(
    private val _delegate : MutableMap<String, Any?> = HashMap()
) : MutableMap<String, Any?> by _delegate{
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

    public fun build(): GuiProperties {
        return GuiProperties(this)
    }
}

