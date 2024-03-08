package app.immich.kmp.module.generator

import app.immich.kmp.module.generator.ui.PortalType
import app.immich.kmp.module.generator.utils.insertMultiline
import java.io.File

internal fun addRoute(
  projectDir: File,
  portalType: PortalType,
  featureName: String,
): Boolean {
  val root = File(projectDir, "router/src/commonMain/kotlin/app/immich/kmp/router")
  val navFile = when(portalType) {
    PortalType.Admin -> File(root, "AdminRoute.kt")
    PortalType.Main -> File(root, "MainRoute.kt")
    PortalType.Root -> File(root, "ImmichRoute.kt")
  }

  return navFile.insertMultiline(
    newLine = "  data object $featureName : ${portalType.name}Route()",
    alphabetizedSectionExtractor = { section -> section.takeWhile { !it.isWhitespace() } },
    lastLineSuffixResolver = "${portalType.name}Route()",
    intoAlphabetizedSectionWithPrefix = arrayOf("  data object ", "  data class "),
  )
}
