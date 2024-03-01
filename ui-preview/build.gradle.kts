plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "app.immich.kmp.ui.preview"
}

kotlin {
  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(compose.runtime)
      }
    }

    jvmMain {
      dependencies {
        api(compose.desktop.currentOs)
      }
    }
  }
}
