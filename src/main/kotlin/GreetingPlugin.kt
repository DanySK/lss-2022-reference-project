package it.unibo.gradle.plugin.greetings

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("Hello World");
    }

}
