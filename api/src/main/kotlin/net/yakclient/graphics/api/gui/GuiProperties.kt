package net.yakclient.graphics.api.gui

import java.util.*

public class GuiProperties(
    delegate: Map<String, Any?>,
) : Map<String, Any?> by delegate {
    public fun <T> getAs(key: String): T? = this[key] as T
}

public const val CHILD_NAME: String = "children"

public class PropertyFactory : MutableMap<String, Any?> by HashMap() {
    init {
        this[CHILD_NAME] = ArrayList<Any>()
    }

    override fun put(key: String, value: Any?): Any? {
        if (
            key.equals(CHILD_NAME, ignoreCase = true)
        ) throw IllegalPropertyException("The name $CHILD_NAME is reserved for children of components")

        this[key.lowercase()] = value

        return value
    }

    public fun addChild(component: ComponentRenderingContext<*>) {
        (this[CHILD_NAME] as MutableList<ComponentRenderingContext<*>>).add(component)
    }

    public fun build(): GuiProperties {
        return GuiProperties(this)
    }
}

