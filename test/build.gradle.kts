group = "net.yakclient"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":util"))
    implementation(project(":components"))
    implementation(project(":api"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}
