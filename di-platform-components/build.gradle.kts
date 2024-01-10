import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
  alias(libs.plugins.ksp)
}

android {
  namespace = "app.immich.kmp.di.platform.components"
}

kotlin {
  defaultKmpTargets(
    project = project,
  )

  kspDependenciesForAllTargets {
    ksp(libs.kotlinInject.compiler)
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(projects.android)
      }
    }

    commonMain {
      dependencies {
        api(projects.diScopes)
      }
    }
  }
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }
}
