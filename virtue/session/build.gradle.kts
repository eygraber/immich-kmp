plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "com.eygraber.virtue.session"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  sourceSets {
    commonMain {
      dependencies {
        api(projects.virtue.backPressDispatch)
        api(projects.virtue.diComponents)
        implementation(projects.virtue.diScopes)
        api(projects.virtue.history)
        implementation(projects.virtue.platform)
        api(projects.virtue.sessionState)
        api(projects.virtue.themeCompose)

        api(compose.material3)

        api(libs.kotlinx.coroutines.core)
        api(libs.portal.compose)
        api(libs.vice.nav)
        api(libs.vice.portal)
      }
    }
  }
}
