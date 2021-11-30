plugins {
    kotlin("kapt")
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":util"))
    implementation("org.lwjgl.lwjgl:lwjgl_util:2.9.3")
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")

    compileOnly("io.github.emilyy-dev:annotated-service-provider:1.0.1")
    kapt("io.github.emilyy-dev:annotated-service-provider:1.0.1")
}