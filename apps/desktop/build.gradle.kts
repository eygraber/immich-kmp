import org.jetbrains.compose.ComposePlugin

plugins {
  kotlin("jvm")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

dependencies {
  implementation(projects.apps.shared)
  implementation(projects.theme)
  implementation(projects.virtue.app)

  implementation(compose.desktop.currentOs)
}

compose {
  desktop.application.mainClass = "app.immich.kmp.ImmichDesktopApplicationKt"
}

dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(ComposePlugin.DesktopDependencies.currentOs)
    }
  }
}
