plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

dependencies {
  implementation(projects.uiSession)

  implementation(compose.desktop.currentOs)
  implementation(compose.material3)
}
