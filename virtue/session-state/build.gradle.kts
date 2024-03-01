plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
}

android {
  namespace = "com.eygraber.virtue.session.state"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        api(projects.virtue.diScopes)
      }
    }
  }
}
