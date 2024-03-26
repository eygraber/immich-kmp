plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
}

dependencies {
  implementation(projects.ksp.generateActualRuntime)

  implementation(libs.kotlinPoet.ksp)
  implementation(libs.ksp)
}

dependencyAnalysis {
  issues {
    onIncorrectConfiguration {
      exclude(libs.ksp)
    }
  }
}
