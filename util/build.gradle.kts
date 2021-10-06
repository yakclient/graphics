plugins {
//    id("org.javamodularity.moduleplugin") version "1.8.10"
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.lwjgl.lwjgl:lwjgl_util:2.9.3")
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")
//    runtimeOnly("org.lwjgl.lwjgl:lwjgl_util:2.9.3")
//    runtimeOnly("org.lwjgl.lwjgl:lwjgl:2.9.3")
//    implementation("org.slick2d:slick2d-core:1.0.2")

}

//modularity.patchModule("lwjgl", "lwjgl-2.9.3.jar")
//modularity.patchModule("lwjgl.util", "lwjgl_util-2.9.3.jar")