plugins {
    `java-gradle-plugin` // Injects the plugin classpath into the tests
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gitSemVer)
    id("com.gradle.plugin-publish") version "1.0.0"
    id("org.danilopianini.gradle-kotlin-qa") version "0.27.0"
    alias(libs.plugins.dokka)
    signing
}

group = "org.danilopianini"

repositories {
    mavenCentral()
}

gitSemVer {
    buildMetadataSeparator.set("-")
    maxVersionLength.set(20)
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

gradlePlugin {
    plugins {
        create("") { // One entry per plugin
            id = "${project.group}.${project.name}"
            displayName = "LSS Greeting plugin"
            description = "Example plugin for the LSS course"
            implementationClass = "it.unibo.gradle.plugin.greetings.GreetingPlugin"
        }
    }
}

pluginBundle { // These settings are set for the whole plugin bundle
    website = "https://danysk.github.io/Course-Laboratory-of-Software-Systems/"
    vcsUrl = "https://github.com/DanySK/Course-Laboratory-of-Software-Systems"
    tags = listOf("example", "greetings", "lss", "unibo")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = true
    }
}

tasks.register<Jar>("javadocJar") {
    from(tasks.dokkaJavadoc.get().outputDirectory)
    archiveClassifier.set("javadoc")
}

tasks.register<Jar>("sourceJar") {
    from(sourceSets.named("main").get().allSource)
    archiveClassifier.set("sources")
}
