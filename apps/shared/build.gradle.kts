plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "app.immich.kmp.shared"
}

kotlin {
  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.features.admin.externalLibraries)
        implementation(projects.features.admin.jobs)
        implementation(projects.features.admin.repair)
        implementation(projects.features.admin.serverStats)
        implementation(projects.features.admin.settings)
        implementation(projects.features.admin.userManagement)
        implementation(projects.features.host.admin)
        implementation(projects.features.host.main)
        implementation(projects.features.main.albums)
        implementation(projects.features.main.archive)
        implementation(projects.features.main.explore)
        implementation(projects.features.main.favorites)
        implementation(projects.features.main.library)
        implementation(projects.features.main.map)
        implementation(projects.features.main.people)
        implementation(projects.features.main.photos)
        implementation(projects.features.main.places)
        implementation(projects.features.main.sharing)
        implementation(projects.features.main.trash)
        implementation(projects.features.main.userSettings)
        implementation(projects.features.root.album)
        implementation(projects.features.root.fourOhFour)
        implementation(projects.features.root.login)
        implementation(projects.features.root.memory)
        implementation(projects.features.root.photo)
        implementation(projects.features.root.search)

        api(projects.core)
      }
    }
  }
}
