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
  id("com.eygraber.conventions.settings") version "0.0.70"
  id("com.gradle.enterprise") version "3.16.1"
}

include(":apps:android")
include(":apps:desktop")
include(":apps:shared")
include(":apps:webJs")
include(":apps:webWasm")
include(":core")
include(":features:admin:external-libraries")
include(":features:admin:jobs")
include(":features:admin:repair")
include(":features:admin:server-stats")
include(":features:admin:settings")
include(":features:admin:user-management")
include(":features:host:admin")
include(":features:host:main")
include(":features:main:albums")
include(":features:main:archive")
include(":features:main:explore")
include(":features:main:favorites")
include(":features:main:library")
include(":features:main:map")
include(":features:main:people")
include(":features:main:photos")
include(":features:main:places")
include(":features:main:sharing")
include(":features:main:trash")
include(":features:main:user-settings")
include(":features:root:album")
include(":features:root:login")
include(":features:root:memory")
include(":features:root:photo")
include(":features:root:four-oh-four")
include(":features:root:search")
include(":icons")
include(":ksp:generate-actual-compiler")
include(":ksp:generate-actual-runtime")
include(":router")
include(":theme")
include(":ui-preview")
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
