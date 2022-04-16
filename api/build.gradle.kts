plugins {
    kotlin("kapt")
    id("maven-publish")
    id("org.jetbrains.dokka")
    id("signing")
}
group = "net.yakclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
}

dependencies {
    api("net.yakclient:event-api:1.0-SNAPSHOT")

    api(project(":util"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.30")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
//    implementation("net.yakclient:event:1.0-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileTestKotlin {
    destinationDirectory.set(tasks.compileTestJava.get().destinationDirectory.get().asFile)
}

tasks.jar {
    this.archiveBaseName.set("graphics-api")
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
        create<MavenPublication>("api-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "graphics-api"

            pom {
                name.set("Graphics API")
                description.set("The API for the YakClient Graphics library")
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
    sign(publishing.publications["api-maven"])
}