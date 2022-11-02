import org.gradle.kotlin.dsl.*

plugins {
    `java-gradle-plugin` // Injects the plugin classpath into the tests
    kotlin("jvm") version "1.7.20"
    id("org.danilopianini.git-sensitive-semantic-versioning-gradle-plugin") version "0.3.20"
}

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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.test {
    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    )
}
val testWithJVM18 by tasks.registering(Test::class) { // Also works with JavaExec
    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(18))
        }
    )
} // You can pick JVM's not yet supported by Gradle!
tasks.check {
    dependsOn(testWithJVM18)
}
