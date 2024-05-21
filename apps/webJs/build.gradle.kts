plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-compose-jetbrains")
  id("com.eygraber.conventions-detekt")
}

kotlin {
  kmpTargets(
    KmpTarget.Js,
    project = project,
    binaryType = BinaryType.Executable,
    webOptions = KmpTarget.WebOptions(
      isNodeEnabled = false,
      isBrowserEnabled = true,
      moduleName = "immich-kmp-js",
    ),
    ignoreDefaultTargets = true,
  )

  js(IR) {
    browser {
      commonWebpackConfig {
        outputFileName = "immich-kmp-js.js"
        experiments += "topLevelAwait"
      }
    }
  }

  sourceSets {
    jsMain {
      dependencies {
        implementation(projects.apps.shared)
        implementation(projects.theme)
        implementation(projects.virtue.app)

        implementation(compose.material3)
      }
    }
  }
}
