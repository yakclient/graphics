group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":opengl2"))

    implementation(project(":components"))
    implementation(project(":api"))
    implementation(project(":util"))
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")

//    implementation(kotlin("stdlib"))
}
tasks.test {
    useJUnitPlatform()
}

tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}