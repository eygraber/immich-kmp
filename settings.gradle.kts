import com.eygraber.conventions.repositories.addCommonRepositories

pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("androidx.*")
      }
    }

    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
      content {
        includeGroupByRegex("org\\.jetbrains.*")
      }
    }

    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots") {
      mavenContent {
        snapshotsOnly()
      }
    }

    maven("https://s01.oss.sonatype.org/content/repositories/snapshots") {
      mavenContent {
        snapshotsOnly()
      }
    }

    gradlePluginPortal()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  // comment this out for now because it doesn't work with KMP js
  // https://youtrack.jetbrains.com/issue/KT-51379/
  // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    addCommonRepositories(
      includeMavenCentral = true,
      includeMavenCentralSnapshots = true,
      includeGoogle = true,
      includeJetbrainsCompose = true,
    )
  }
}

rootProject.name = "immich-kmp"

plugins {
  id("com.eygraber.conventions.settings") version "0.0.69"
  id("com.gradle.enterprise") version "3.16.1"
}

include(":apps:android")
include(":apps:desktop")
include(":apps:shared")
include(":apps:webJs")
include(":apps:webWasm")
include(":core")
include(":icons")
include(":router")
include(":screens:hello-world")
include(":theme")
include(":virtue:android")
include(":virtue:app")
include(":virtue:back-press-dispatch")
include(":virtue:config")
include(":virtue:di-components")
include(":virtue:di-scopes")
include(":virtue:history")
include(":virtue:platform")
include(":virtue:session")
include(":virtue:session-state")
include(":virtue:theme")
include(":virtue:theme-compose")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    if (System.getenv("CI") != null) {
      termsOfServiceAgree = "yes"
      publishAlways()
    }
  }
}
