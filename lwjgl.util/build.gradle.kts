group = "net.yakclient"
version = "1.0-SNAPSHOT"


val nativeBundle: Configuration by configurations.creating
val nativeDir = File("$buildDir/libs/natives")

dependencies {
    val nativeClassifier = properties["lwjgl.natives.classifier"]
    val lwjglVersion = project.ext.get("lwjgl.version")

    implementation(project(":util"))
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")

    nativeBundle("org.lwjgl:lwjgl:$lwjglVersion:$nativeClassifier")
    nativeBundle("org.lwjgl:lwjgl-glfw:$lwjglVersion:$nativeClassifier")
    nativeBundle("org.lwjgl:lwjgl-opengl:$lwjglVersion:$nativeClassifier")
}

val extractNativeBundle = tasks.create<Sync>("extractNativeBundle") {
    fun String.endsWithAny(vararg suffixes: String) = suffixes.any { endsWith(it) }

    val files = nativeBundle
        .map(::zipTree)
        .flatMap(FileTree::getFiles)
        .filter { it.name.endsWithAny("dylib", "os", "dll") }

    from(files)

    into(file(nativeDir))
}

tasks.test {
    dependsOn(extractNativeBundle)

    systemProperty("org.lwjgl.librarypath", nativeDir)
    minHeapSize = "256m"
    maxHeapSize = "1G"
    jvmArgs = listOf(
        "-XstartOnFirstThread",
        "-Dorg.lwjgl.util.Debug=true",
        "-Xms512m", "-Xmx1G"
    )
}