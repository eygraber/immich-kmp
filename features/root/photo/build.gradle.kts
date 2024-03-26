plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
  alias(libs.plugins.ksp)
}

android {
  namespace = "app.immich.kmp.features.root.photo"
}

kotlin {
  defaultKmpTargets(
    project = project,
  )

  kspDependenciesForAllTargets {
    ksp(libs.kotlinInject.compiler)
    ksp(projects.ksp.generateActualCompiler)
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.core)
        implementation(projects.ksp.generateActualRuntime)
        implementation(projects.router)
        implementation(projects.theme)
        implementation(projects.uiPreview)
      }
    }
  }
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}
