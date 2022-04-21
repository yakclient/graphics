plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.4.32"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.javamodularity.moduleplugin") version "1.8.10"

}

group = "net.yakclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}
tasks.wrapper {
    gradleVersion = "7.3.1"
}


nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(project.findProperty("mavenUsername") as String)
            password.set(project.findProperty("mavenPassword") as String)
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.javamodularity.moduleplugin")

    project.ext.set("lwjgl.version", "3.3.1")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    kotlin {
        explicitApi()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        testImplementation(kotlin("test"))
    }

    tasks.compileKotlin {
        destinationDirectory.set(tasks.compileJava.get().destinationDirectory.asFile.get())

        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.compileJava {
        targetCompatibility = "17"
        sourceCompatibility = "17"
    }
}