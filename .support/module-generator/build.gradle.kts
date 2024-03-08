import com.eygraber.conventions.detekt.configureDetekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
  dependencies {
    classpath(libs.buildscript.composeJetbrains)
    classpath(libs.buildscript.detekt)
    classpath(libs.buildscript.kotlin)
  }
}

plugins {
  kotlin("jvm") version libs.versions.kotlin.get()
  id("com.eygraber.conventions-compose-jetbrains") version libs.versions.conventions.get()
  alias(libs.plugins.detekt)
}

configureDetekt(
  jvmTargetVersion = JvmTarget.JVM_21,
  useRootConfigFile = false,
  useProjectConfigFile = false,
  configFiles = files("../../detekt.yml"),
)

dependencies {
  implementation(compose.desktop.currentOs)

  implementation(compose.material3)

  detektPlugins(libs.detektCompose)
  detektPlugins(libs.detektEygraber.formatting)
  detektPlugins(libs.detektEygraber.style)
}

compose.desktop {
  application {
    mainClass = "app.immich.kmp.module.generator.ui.ModuleGeneratorKt"
  }
}

gradleConventions {
  compose {
    multiplatform(
      compilerOverride = libs.compose.compilerJetbrains,
    )
  }

  kotlin {
    jvmTargetVersion = JvmTarget.JVM_21
    allWarningsAsErrors = true
  }
}
