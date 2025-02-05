pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "GithubRepository"
include(":app")

include(":feature:main")
include(":feature:repository")
include(":feature:search")

include(":core:designsystem")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:data-api")
include(":core:navigation")
