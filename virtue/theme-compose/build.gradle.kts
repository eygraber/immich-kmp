plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "com.eygraber.virtue.theme.compose"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        api(projects.virtue.theme)

        implementation(compose.material3)
      }
    }
  }
}
