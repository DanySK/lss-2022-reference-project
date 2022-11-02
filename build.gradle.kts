import org.gradle.kotlin.dsl.*

plugins {
    `java-gradle-plugin` // Injects the plugin classpath into the tests
    kotlin("jvm") version "1.7.20"
    id("org.danilopianini.git-sensitive-semantic-versioning-gradle-plugin") version "0.3.20"
}

version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    testImplementation(gradleTestKit())
    testImplementation(libs.bundles.kotlin.testing)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
