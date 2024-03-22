package app.immich.kmp.module.generator

import app.immich.kmp.module.generator.ui.PortalType
import app.immich.kmp.module.generator.utils.insert
import app.immich.kmp.module.generator.utils.kebabCaseToCamelCase
import java.io.File

internal fun addModuleToAppDependencies(
  projectDir: File,
  moduleName: String,
): Boolean {
  val appGradleBuildFile = File(projectDir, "apps/shared/build.gradle.kts")
  val moduleProjectName = moduleName.replace(":", ".").kebabCaseToCamelCase(upperCamelCase = false)

  return appGradleBuildFile.insert(
    newLine = "        implementation(projects.features.$moduleProjectName)",
    intoAlphabetizedSectionWithPrefix = "        implementation(projects.features.",
  )
}

internal fun addToPortalFactory(
  projectDir: File,
  packageName: String,
  portalType: PortalType,
  portalName: String,
) {
  with(File(projectDir, "apps/shared/src/commonMain/kotlin/app/immich/kmp/ImmichPortalFactory.kt")) {
    insert(
      newLine = "import $packageName.${portalName}PortalFactory",
      intoAlphabetizedSectionWithPrefix = "import ",
    )

    insert(
      newLine = "      is ${portalType.name}Route.$portalName -> ${portalName}PortalFactory(route, component)",
      intoAlphabetizedSectionWithPrefix = "      is ${portalType.name}Route.",
    )
  }
}
