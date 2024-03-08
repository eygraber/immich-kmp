package app.immich.kmp.module.generator

import app.immich.kmp.module.generator.utils.insert
import java.io.File

internal fun addModuleToSettings(
  projectDir: File,
  moduleName: String,
): Boolean {
  val settingsFile = File(projectDir, "settings.gradle.kts")

  return settingsFile.insert(
    newLine = "include(\":features:$moduleName\")",
    intoAlphabetizedSectionWithPrefix = "include(",
  )
}
