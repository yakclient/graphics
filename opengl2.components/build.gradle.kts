plugins {
    kotlin("kapt")
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":opengl2"))
    implementation(project(":components"))
    implementation(project(":api"))
    implementation(project(":util"))
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")
    compileOnly("io.github.emilyy-dev:annotated-service-provider:1.0.1")
    kapt("io.github.emilyy-dev:annotated-service-provider:1.0.1")

    testImplementation(project(":opengl2.util"))
}
tasks.test {
    useJUnitPlatform()
}
//
tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}