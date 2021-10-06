
group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
//    implementation(fileTree("lib") { include("*.jar") })
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")

//    implementation(kotlin("stdlib"))
    implementation(project(":api"))
    implementation(project(":util"))
    testImplementation(project(":components"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}