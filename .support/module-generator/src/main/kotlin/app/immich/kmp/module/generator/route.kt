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
    PortalType.Host -> File(root, "HostRoute.kt")
    PortalType.Main -> File(root, "MainRoute.kt")
    PortalType.Root -> File(root, "RootRoute.kt")
  }

  return navFile.insertMultiline(
    newLine = "  data object $featureName : ${portalType.name}Route",
    alphabetizedSectionExtractor = { section -> section.takeWhile { !it.isWhitespace() } },
    lastLineSuffixResolver = "  }",
    intoAlphabetizedSectionWithPrefix = arrayOf("  data object ", "  data class "),
  )
}
