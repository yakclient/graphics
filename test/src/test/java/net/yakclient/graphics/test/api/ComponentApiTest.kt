package net.yakclient.graphics.test.api

import kotlin.reflect.KFunction

typealias Renderer = UIComponent.(props: Any) -> Nothing

 interface UIComponent {
    fun render(props: Any)

    fun setProp(value: Any) {}

    fun build(func: KFunction<UIComponent>, builder: ComponentScope.() -> Unit) {}

//    protected fun getValue() : Any? = null
}

fun basicComponent() : Renderer = { props ->
    val property1 = props["hey"]

    val state = state[""] = "blah blah lbah"

    build(::otherComponent) {
        set(something, soemthinglese)

        build(::basicComponent) {
            again....

            askdfjkasjdf
        }
    }

//    setPr
//    getProp<String>("")
//    build(::otherComponent) {
//        set()


//    }
}

fun otherComponent() = UIComponent { props, state -> }

class ComponentScope {
    fun set(value: Any) {}
    fun build(func: KFunction<UIComponent>, builder: ComponentScope.() -> Unit) {}

    /*etc...*/
}

