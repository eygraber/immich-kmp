plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "com.eygraber.virtue.app"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    androidMain {
      dependencies {
        api(libs.androidx.activityCompose)
      }
    }

    commonMain {
      dependencies {
        api(projects.virtue.session)
        implementation(projects.virtue.themeCompose)

        implementation(compose.foundation)
        api(compose.material3)
      }
    }
  }
}
