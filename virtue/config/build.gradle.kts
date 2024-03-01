plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
}

android {
  namespace = "com.eygraber.virtue.config"
}

kotlin {
  explicitApi()

  defaultKmpTargets(
    project = project,
  )
}
