package net.yakclient.graphics.components

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.util.PaddingFunc
import net.yakclient.graphics.util.ColorFunction
import net.yakclient.graphics.util.VacantColorFunction
import java.io.InputStream

public fun TextBox(): Component = { props ->
    val specs = TextSpecProps(props)
    val padding: PaddingFunc = props.getAs<PaddingFunc>("padding") ?: PaddingFunc(0.0)
    val backgroundColor: ColorFunction = props.getAs("backgroundcolor") ?: VacantColorFunction()
    val backgroundImage = props.getAs<InputStream>("backgroundimage")

    build(use<Box>(0)) {
        set("x") to specs.x
        set("y") to specs.y
        set("width") to specs.font.getWidth(specs.value) + padding.paddingHorizontal
        set("height") to specs.font.getWidth(specs.value) + padding.paddingHorizontal
        set("backgroundcolor") to backgroundColor
        set("backgroundimage") ifNotNull backgroundImage

        build(use<Text>(1)) {
            set("x") to specs.x + padding.paddingLeft
            set("y") to specs.y + padding.paddingTop

            set("value") to specs.value
            set("color") to specs.color
            set("font") to specs.font
        }
    }
}
//
//class TextBox : GuiComponent() {
//    override fun render(properties: GuiProperties): ComponentRenderingContext<*> {
//        val specs = TextSpecProps(properties)
//        val padding = this.requestProp(properties.get("padding")).or { Optional.of(PaddingFunc(0)) }
//            .get()
//        val backgroundColor = this.requestProp(properties.get("backgroundcolor"))
//        val backgroundImage = this.requestProp(properties.get("backgroundimage"))
//        return this.create(this.useComponent(Box(), 0))
//            .addProp("x", specs.x)
//            .addProp("y", specs.y)
//            .addProp("width", specs.font.getWidth(specs.value) + padding.getPaddingHorizontal())
//            .addProp("height", specs.font.getHeight(specs.value) + padding.getPaddingVertical())
//            .addProp("backgroundcolor", backgroundColor.isPresent(), backgroundColor::get)
//            .addProp("backgroundimage", backgroundImage.isPresent(), backgroundImage::get)
//            .addChild(this.create(this.useComponent(Text(), 1))
//                .addProp("x", specs.x + padding.getPaddingLeft()) //Assuming the origin is the top left
//                .addProp("y", specs.y + padding.getPaddingTop())
//                .addProp("value", specs.value)
//                .addProp("color", specs.color)
//                .addProp("font", specs.font)
//            )
//            .build()
//    }
//}