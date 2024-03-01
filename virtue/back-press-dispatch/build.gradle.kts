plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
  id("kotlinx-atomicfu")
}

android {
  namespace = "com.eygraber.virtue.back.press.dispatch"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    androidMain {
      dependencies {
        implementation(libs.androidx.activityCompose)
      }
    }

    commonMain {
      dependencies {
        implementation(projects.virtue.diScopes)
        implementation(projects.virtue.platform)

        implementation(compose.foundation)
        api(compose.runtime)
        implementation(compose.ui)

        implementation(libs.kotlinInject.runtime)
      }
    }
  }
}
