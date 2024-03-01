import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("kotlinx-atomicfu")
  alias(libs.plugins.kotlinxSerialization)
}

android {
  namespace = "com.eygraber.virtue.history"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  applyDefaultHierarchyTemplate {
    common {
      group("notWeb") {
        withAndroidTarget()
        withJvm()
        withIos()
      }
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.virtue.diScopes)
        implementation(projects.virtue.backPressDispatch)
        api(projects.virtue.sessionState)

        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinInject.runtime)
      }
    }
  }
}
