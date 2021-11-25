plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("kapt") version "1.5.31"
    id("org.javamodularity.moduleplugin") version "1.8.10"

}

group = "net.yakclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.wrapper {
    gradleVersion = "7.2"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin="org.javamodularity.moduleplugin")

    repositories {
        mavenCentral()
    }

    kotlin {
        explicitApi()
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }

    tasks.compileJava {
        dependsOn(tasks.clean)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        destinationDirectory.set(tasks.compileJava.get().destinationDirectory.asFile.get())
        kotlinOptions.jvmTarget = "11"
    }
}