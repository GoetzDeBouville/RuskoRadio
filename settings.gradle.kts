pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rusko Radio"
include(":app")
include(":core:itunesservice")
include(":core:presentation")
include(":core:di-qualifiers")
include(":core:extentions")
