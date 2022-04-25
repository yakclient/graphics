plugins {
    kotlin("kapt")
    id("maven-publish")
    id("org.jetbrains.dokka")
    id("signing")
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"



dependencies {
    implementation(project(":lwjgl.legacy"))
    implementation(project(":components"))
    api(project(":api"))
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.3")

    testImplementation(project(":lwjgl.legacy.util"))
}
tasks.test {
    useJUnitPlatform()
}
//
tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}

tasks.jar {
    this.archiveBaseName.set("graphics-opengl2-components")
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
        create<MavenPublication>("opengl2-components-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "graphics-opengl2-components"

            pom {
                name.set("Graphics OpenGL2 Components")
                description.set("Implementation for the YakClient components module for opengl2")
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

signing {
    sign(publishing.publications["opengl2-components-maven"])
}