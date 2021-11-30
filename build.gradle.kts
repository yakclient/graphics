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
    apply(plugin = "org.javamodularity.moduleplugin")

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

//    sourceSets {
//        main {
//            java {
//                resources.destinationDirectory.set(java.destinationDirectory)
//            }
//        }
//    }

//    tasks.processResources {
//        println(destinationDir.absolutePath)
//    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        destinationDirectory.set(tasks.compileJava.get().destinationDirectory.asFile.get())
//       sourceSets.main.get().resources.destinationDirectory.set(tasks.compileJava.get().destinationDirectory)
//        println("The dir is" + sourceSets.main.get().resources.destinationDirectory.get().asFile.absoluteFile)

//        resources.
//        tasks.proce
        kotlinOptions.jvmTarget = "11"
    }
}