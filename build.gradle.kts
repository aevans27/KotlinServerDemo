val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
plugins {
    application
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "com.aevans"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")

    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:1.6.4")
    implementation("io.ktor:ktor-server-netty:1.6.4")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("io.ktor:ktor-serialization:1.6.4")
    implementation("io.ktor:ktor-html-builder:1.6.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}