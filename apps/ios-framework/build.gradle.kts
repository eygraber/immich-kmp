import com.eygraber.conventions.kotlin.kmp.spm.registerAssembleXCFrameworkTasksFromFrameworks
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-detekt")
}

kotlin {
  kmpTargets(
    KmpTarget.Ios,
    project = project,
  )

  targets.withType<KotlinNativeTarget> {
    if(konanTarget.family.isAppleFamily) {
      binaries.framework {
        baseName = "ImmichKmp"
        export(projects.virtue.app)
      }
    }
  }

  project.registerAssembleXCFrameworkTasksFromFrameworks(
    frameworkName = "ImmichKmp",
  )

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.apps.shared)
        api(projects.virtue.app)
      }
    }
  }
}
