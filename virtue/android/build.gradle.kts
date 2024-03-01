plugins {
  kotlin("android")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-android-library")
  id("com.eygraber.conventions-detekt")
}

android {
  namespace = "com.eygraber.virtue.android"
}

kotlin {
  explicitApi()
}
