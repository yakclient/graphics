plugins {
    id("org.jetbrains.dokka")
}
group = "net.yakclient"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":util"))
    implementation(project(":api"))
//    implementation(project(":opengl2"))

}

tasks.test {
    useJUnitPlatform()
}

tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)

 }


tasks.jar {
    this.archiveBaseName.set("graphics-components")
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
        create<MavenPublication>("components-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "graphics-components"

            pom {
                name.set("Graphics Components")
                description.set("Useful components for dealing with the YakClient graphics library")
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
//    sign(publishing.publications["components-maven"])
//}
