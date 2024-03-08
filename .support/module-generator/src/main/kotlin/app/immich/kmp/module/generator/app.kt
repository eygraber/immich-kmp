package app.immich.kmp.module.generator

import app.immich.kmp.module.generator.utils.insert
import app.immich.kmp.module.generator.utils.kebabCaseToCamelCase
import java.io.File

internal fun addModuleToAppDependencies(
  projectDir: File,
  moduleName: String,
): Boolean {
  val appGradleBuildFile = File(projectDir, "apps/shared/build.gradle.kts")
  val moduleProjectName = moduleName.kebabCaseToCamelCase(upperCamelCase = false)

  return appGradleBuildFile.insert(
    newLine = "  implementation(projects.features.$moduleProjectName)",
    intoAlphabetizedSectionWithPrefix = "  implementation(projects.features.",
  )
}

internal fun addToPortalFactory(
  projectDir: File,
  packageName: String,
  portalName: String,
) {
  with(File(projectDir, "apps/shared/src/commonMain/kotlin/app/immich/kmp/ImmichPortalFactory.kt")) {
    insert(
      newLine = "import $packageName.$portalName",
      intoAlphabetizedSectionWithPrefix = "import ",
    )

    insert(
      newLine = "      is ImmichNav.$portalName -> ${portalName}PortalFactory(route, component)",
      intoAlphabetizedSectionWithPrefix = "      is ImmichNav.",
    )
  }
}
