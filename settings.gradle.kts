pluginManagement {
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
    //dagger dependency is not working is because of this
    //https://stackoverflow.com/questions/69511275/plugin-id-dagger-hilt-android-plugin-was-not-found-in-any-of-the-following
    resolutionStrategy {
        eachPlugin {
            if( requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AnytimeMemo"
include(":app")
 