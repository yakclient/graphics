open module yak.opengl.test {
    requires yakclient.graphics.util;
    requires yak.opengl.components;
    requires yakclient.graphics.api;

    requires org.jetbrains.annotations;

    requires lwjgl;
    requires lwjgl.util;
    requires slick.util;
    requires java.desktop;
    requires org.junit.jupiter.api;

    requires org.opentest4j;
    requires org.junit.platform.commons;
    requires org.apiguardian.api;

    requires jdk.unsupported;
    requires kotlin.stdlib;
}