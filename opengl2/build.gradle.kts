plugins {
    kotlin("kapt")
}
group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")

    implementation(project(":api"))
    implementation(project(":util"))
    compileOnly("io.github.emilyy-dev:annotated-service-provider:1.0.1")
    kapt("io.github.emilyy-dev:annotated-service-provider:1.0.1")
}

//tasks.test {
//    useJUnitPlatform()
//}
//
//tasks.compileTestKotlin {
//    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
//}