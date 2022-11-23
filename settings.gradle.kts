plugins {
    id("com.gradle.enterprise") version "3.11.1"
}

rootProject.name = "lss2022.helloplugin"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

val destination = File(".git/hooks/commit-msg")
File("check-commit.sh")
    .copyTo(destination, overwrite = true)
destination.setExecutable(true)
