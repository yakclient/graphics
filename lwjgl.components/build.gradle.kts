group = "net.yakclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


val nativeBundle: Configuration by configurations.creating
val nativeDir = File("$buildDir/libs/natives")

dependencies {
    val nativeClassifier = properties["lwjgl.natives.classifier"]
    val lwjglVersion = project.ext.get("lwjgl.version")

    implementation(project(":api"))
    implementation(project(":components"))
    implementation(project(":util"))
    implementation(project(":lwjgl"))
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")

    implementation(project(":lwjgl.util"))

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

    minHeapSize = "512m"
    maxHeapSize = "4G"

    jvmArgs = listOf(
        "-XstartOnFirstThread",
        "-Dorg.lwjgl.util.Debug=true",
        "--add-reads",
        "kotlin.stdlib=kotlinx.coroutines.core.jvm",
        "-Xms512m", "-Xmx4G"
    )
}