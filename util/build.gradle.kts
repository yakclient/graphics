plugins {
    id("org.jetbrains.dokka")
}
group = "net.yakclient"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}

tasks.jar {
    this.archiveBaseName.set("graphics-util")
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
        create<MavenPublication>("util-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "graphics-util"

            pom {
                name.set("Graphics Util")
                description.set("Utils for working with the YakClient graphics library")
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
//    sign(publishing.publications["util-maven"])
//}