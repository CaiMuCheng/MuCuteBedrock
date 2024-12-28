plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.mucheng.mucute.bedrock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-network:3.0.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}