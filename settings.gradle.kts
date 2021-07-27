rootProject.name = "telegram-api"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven(url = "https://artfable.jfrog.io/artifactory/default-maven-local")
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace?.startsWith("artfable") == true) {
                useModule("${extra["custom.plugins.${requested.id}"]}:${requested.version}")
            }
        }
    }
}

