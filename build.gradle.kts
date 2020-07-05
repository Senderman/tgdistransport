import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "5.2.0"

    kotlin("jvm") version "1.3.72"
}

group = "com.senderman"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11
application.mainClassName = "com.senderman.tgdistransport.TgDisTransportApplicationKt"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.discord4j:discord4j-core:3.1.0.RC2")
    implementation("org.telegram:telegrambots:4.9")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.shadowJar{
    mergeServiceFiles()
}
