plugins {
    kotlin("kapt")
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":util"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.30")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
//    testCompileOnly
    testCompileOnly("io.github.emilyy-dev:annotated-service-provider:1.0.1")
    kapt("io.github.emilyy-dev:annotated-service-provider:1.0.1")
}

tasks.test {
    useJUnitPlatform()
}
//
tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}