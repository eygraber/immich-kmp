plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.eygraber.virtue.di.components"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )

  kspDependenciesForAllTargets {
    ksp(libs.kotlinInject.compiler)
    ksp(projects.ksp.generateActualCompiler)
  }

  sourceSets {
    androidMain {
      dependencies {
        api(projects.virtue.android)
      }
    }

    commonMain {
      dependencies {
        implementation(projects.ksp.generateActualRuntime)
        api(projects.virtue.config)
        api(projects.virtue.diScopes)
        api(projects.virtue.theme)
      }
    }
  }
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}
