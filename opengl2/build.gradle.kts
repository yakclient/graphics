
group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
//    implementation(fileTree("lib") { include("*.jar") })
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.2")

    implementation(kotlin("stdlib"))
    implementation(project(":api"))
    implementation(project(":util"))
}