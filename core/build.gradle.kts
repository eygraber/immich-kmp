plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
  alias(libs.plugins.ksp)
}

android {
  namespace = "app.immich.kmp.core"
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
        api(projects.router)
        api(projects.virtue.diComponents)
        api(projects.virtue.session)
      }
    }

    iosMain {
      dependencies {
        implementation(projects.ksp.generateActualRuntime)
      }
    }
  }
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}
