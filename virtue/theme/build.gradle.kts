plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
}

android {
  namespace = "com.eygraber.virtue.theme"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.virtue.config)
        implementation(projects.virtue.diScopes)

        api(libs.kotlinx.coroutines.core)
      }
    }
  }
}
