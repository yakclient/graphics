group = "net.yakclient"
version = "1.0-SNAPSHOT"

//val unpackNatives: Configuration by configurations.creating

dependencies {
    implementation(project(":util"))
    implementation(project(":api"))
    implementation(project(":opengl2"))
//    implementation(project(":opengl2.components"))

//    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")
//    implementation("org.lwjgl.lwjgl:lwjgl-platform:2.9.3")
//    testImplementation(":components")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

//tasks.create<Sync>("unpackNatives") {
//    println(unpackNatives.dependencies.map { zipTree(it) }.filter { it.any { f -> f.endsWith(".so") || f.endsWith(".dylib") || f.endsWith(".dll") }})
////    from(unpackNatives.dependencies.map { zipTree(it) }.filter { it.any { f -> f.endsWith(".so") || f.endsWith(".dylib") || f.endsWith(".dll") }})
//
////    into("$buildDir/libs/natives")
////    unpackNatives.dependencies
//}

tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)

 }

