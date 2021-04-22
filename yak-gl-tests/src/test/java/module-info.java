module yak.gl.tests {
    requires yak.gl.util;
    requires yak.gl.components;
    requires yak.gl.api;

    requires org.jetbrains.annotations;

    requires lwjgl;
    requires lwjgl.util;
    requires slick.util;
    requires java.desktop;
    requires org.junit.jupiter.api;

    requires org.opentest4j;
    requires org.junit.platform.commons;
    requires org.apiguardian.api;

    //Kill me now but lwjgl needs it
    requires jdk.unsupported;
}