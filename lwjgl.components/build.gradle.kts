plugins {
    id("org.jetbrains.dokka")
}

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

    api(project(":api"))
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

tasks.jar {
    this.archiveBaseName.set("graphics-lwjgl-components")
}

task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
}

publishing {
    publications {
        create<MavenPublication>("lwjgl-components-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "graphics-lwjgl-components"

            pom {
                name.set("Graphics LWJGL Components")
                description.set("Implementation for the YakClient graphics component library for LWJGL")
                url.set("https://github.com/yakclient/graphics")

                packaging = "jar"

                developers {
                    developer {
                        id.set("Chestly")
                        name.set("Durgan McBroom")
                    }
                }

                licenses {
                    license {
                        name.set("GNU General Public License")
                        url.set("https://opensource.org/licenses/gpl-license")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/yakclient/graphics")
                    developerConnection.set("scm:git:ssh://github.com:yakclient/graphics.git")
                    url.set("https://github.com/yakclient/graphics")
                }
            }
        }
    }
}

//signing {
//    sign(publishing.publications["lwjgl-components-maven"])
//}