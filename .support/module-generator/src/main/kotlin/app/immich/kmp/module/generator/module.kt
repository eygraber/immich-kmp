package app.immich.kmp.module.generator

import app.immich.kmp.module.generator.ui.PortalType
import app.immich.kmp.module.generator.utils.div
import java.io.File

internal fun createModule(
  projectDir: File,
  moduleName: String,
  packageName: String,
  featureName: String,
  shouldGeneratePreview: Boolean,
  shouldGeneratePreviewParameterProvider: Boolean,
  portalType: PortalType,
) {
  val featuresDir = File(projectDir, "features")
  val moduleDir = File(featuresDir, moduleName).apply { mkdir() }
  val commonMainDir = File(moduleDir, "src" / "commonMain").apply { mkdirs() }
  val androidDir = File(moduleDir, "src" / "androidMain").apply { mkdirs() }
  val iosDir = File(moduleDir, "src" / "iosMain").apply { mkdirs() }
  val jsDir = File(moduleDir, "src" / "jsMain").apply { mkdirs() }
  val jvmDir = File(moduleDir, "src" / "jvmMain").apply { mkdirs() }
  val wasmJsDir = File(moduleDir, "src" / "wasmJsMain").apply { mkdirs() }
  val packagePath = packageName.replace(".", File.separator)
  val commonMainPackageDir = File(commonMainDir, "kotlin" / packagePath).apply { mkdirs() }
  val androidPackageDir = File(androidDir, "kotlin" / packagePath).apply { mkdirs() }
  val iosPackageDir = File(iosDir, "kotlin" / packagePath).apply { mkdirs() }
  val jsPackageDir = File(jsDir, "kotlin" / packagePath).apply { mkdirs() }
  val jvmPackageDir = File(jvmDir, "kotlin" / packagePath).apply { mkdirs() }
  val wasmJsPackageDir = File(wasmJsDir, "kotlin" / packagePath).apply { mkdirs() }

  File(moduleDir, "build.gradle.kts").apply {
    if(!exists()) {
      createNewFile()

      writeText(
        """
        |plugins {
        |  id("com.eygraber.conventions-kotlin-multiplatform")
        |  id("com.eygraber.conventions-android-library")
        |  id("com.eygraber.conventions-detekt")
        |  id("com.eygraber.conventions-compose-jetbrains")
        |  alias(libs.plugins.ksp)
        |}
        |
        |android {
        |  namespace = "$packageName"
        |}
        |
        |kotlin {
        |  defaultKmpTargets(
        |    project = project,
        |  )
        |  
        |  kspDependenciesForAllTargets {
        |    ksp(libs.kotlinInject.compiler)
        |  }
        |  
        |  sourceSets {
        |    commonMain {
        |      dependencies {
        |        implementation(projects.core)
        |        implementation(projects.router)
        |        implementation(projects.theme)
        |        implementation(projects.uiPreview)
        |      }
        |    }
        |  }
        |}
        |
        |ksp {
        |  arg("me.tatarka.inject.generateCompanionExtensions", "true")
        |}
        |
        """.trimMargin(),
      )
    }
  }

  File(moduleDir, "consumer-rules.pro").apply {
    if(!exists()) {
      createNewFile()
      writeText("")
    }
  }

  val routeType = "ImmichRoute.${portalType.name}.$featureName"

  val portalName = "${featureName}Portal"
  val componentName = "${featureName}Component"
  val compositorName = "${featureName}Compositor"
  val effectsName = "${featureName}Effects"
  val intentName = "${featureName}Intent"
  val viewName = "${featureName}View"
  val viewStateName = "${featureName}ViewState"
  val viewStatePreviewProviderName = "${viewStateName}PreviewProvider"

  File(commonMainPackageDir, "$portalName.kt").apply {
    if(!exists()) {
      createNewFile()
      writeText(
        """
        |package $packageName
        |
        |import app.immich.kmp.core.ImmichSessionComponent
        |import app.immich.kmp.core.ImmichSessionScreen
        |import app.immich.kmp.core.ImmichSessionScreenComponent
        |import app.immich.kmp.router.ImmichNav
        |import com.eygraber.virtue.di.scopes.SessionScreenSingleton
        |import com.eygraber.virtue.session.GenericVirtueScreen
        |import me.tatarka.inject.annotations.Component
        |
        |internal typealias Route = $routeType
        |internal typealias View = $viewName
        |internal typealias Intent = $intentName
        |internal typealias Compositor = $compositorName
        |internal typealias Effects = $effectsName
        |internal typealias ViewState = $viewStateName
        |
        |object ${portalName}Factory {
        |  operator fun invoke(
        |    route: Route,
        |    parentComponent: ImmichSessionComponent,
        |  ): GenericVirtueScreen<Route> = $portalName(route, parentComponent)
        |}
        |
        |internal class $portalName(
        |  override val key: Route,
        |  override val parentComponent: ImmichSessionComponent,
        |) : ImmichSessionScreen<Route, View, Intent, Compositor, Effects, ViewState>() {
        |  // TODO: https://github.com/evant/kotlin-inject/pull/362
        |  override val component = $componentName.createA(
        |    sessionComponent = parentComponent,
        |  )
        |}
        |
        |@SessionScreenSingleton
        |@Component
        |internal abstract class $componentName(
        |  @Component override val parentComponent: ImmichSessionComponent,
        |) : ImmichSessionScreenComponent<View, Intent, Compositor, Effects, ViewState> {
        |  companion object
        |}
        |
        |internal expect fun $componentName.Companion.createA(
        |  sessionComponent: ImmichSessionComponent,
        |): $componentName
        |
        """.trimMargin(),
      )
    }
  }

  File(commonMainPackageDir, "$compositorName.kt").apply {
    if(!exists()) {
      createNewFile()
      writeText(
        """
        |package $packageName
        |
        |import androidx.compose.runtime.Composable
        |import com.eygraber.vice.ViceCompositor
        |import com.eygraber.virtue.di.scopes.SessionScreenSingleton
        |import kotlinx.coroutines.flow.Flow
        |import me.tatarka.inject.annotations.Inject
        |
        |@SessionScreenSingleton
        |@Inject
        |internal class $compositorName : ViceCompositor<Intent, ViewState>() {
        |  @Composable
        |  override fun composite(intents: Flow<Intent>) = ViewState
        |}
        |
        """.trimMargin(),
      )
    }
  }

  File(commonMainPackageDir, "$effectsName.kt").apply {
    if(!exists()) {
      createNewFile()
      writeText(
        """
        |package $packageName
        |
        |import com.eygraber.vice.ViceEffects
        |import com.eygraber.virtue.di.scopes.SessionScreenSingleton
        |import kotlinx.coroutines.CoroutineScope
        |import me.tatarka.inject.annotations.Inject
        |
        |@SessionScreenSingleton
        |@Inject
        |internal class $effectsName : ViceEffects() {
        |  override fun CoroutineScope.onInitialized() {}
        |}
        |
        """.trimMargin(),
      )
    }
  }

  File(commonMainPackageDir, "$intentName.kt").apply {
    if(!exists()) {
      createNewFile()
      writeText(
        """
        |package $packageName
        |
        |internal sealed interface $intentName
        |
        """.trimMargin(),
      )
    }
  }

  File(commonMainPackageDir, "$viewName.kt").apply {
    if(!exists()) {
      createNewFile()

      val previewImports = when {
        shouldGeneratePreview -> when {
          shouldGeneratePreviewParameterProvider ->
            listOf(
              "androidx.compose.ui.tooling.preview.PreviewParameter",
              "androidx.compose.desktop.ui.tooling.preview.Preview",
            )

          else -> listOf(
            "androidx.compose.desktop.ui.tooling.preview.Preview",
          )
        }

        else -> emptyList()
      }

      val preview = when {
        shouldGeneratePreview -> when {
          shouldGeneratePreviewParameterProvider ->
            """
            |
            |@Preview
            |@Composable
            |private fun ${featureName}Preview(
            |  @PreviewParameter(ViewStatePreviewProvider::class) state: ViewState
            |) {
            |  ImmichMaterialTheme {
            |    $featureName(
            |      state = state,
            |      onIntent = {},
            |    )
            |  }
            |}
            |
            """.trimMargin()

          else ->
            """
            |
            |@Preview
            |@Composable
            |private fun ${featureName}Preview() {
            |  ImmichMaterialTheme {
            |    $featureName(
            |      state = ViewState,
            |      onIntent = {},
            |    )
            |  }
            |}
            |
            """.trimMargin()
        }

        else -> ""
      }

      val themeImport = when {
        shouldGeneratePreview || shouldGeneratePreviewParameterProvider ->
          "app.immich.kmp.theme.ImmichMaterialTheme"
        else -> null
      }

      val imports = (
        previewImports +
          listOf(themeImport) +
          listOf(
            "androidx.compose.runtime.Composable",
            "com.eygraber.vice.ViceView",
            "com.eygraber.virtue.di.scopes.SessionScreenSingleton",
            "me.tatarka.inject.annotations.Inject",
          )
        )
        .filterNotNull()
        .sorted()
        .joinToString(separator = "\n") {
          "import $it"
        }

      writeText(
        """
        |package $packageName
        |
        |$imports
        |
        |@SessionScreenSingleton
        |@Inject
        |internal class $viewName : ViceView<Intent, ViewState> {
        |  @Composable
        |  override fun Render(state: ViewState, onIntent: (Intent) -> Unit) {
        |    $featureName(state = state, onIntent = onIntent)
        |  }
        |}
        |
        |@Suppress("UNUSED_PARAMETER")
        |@Composable
        |private fun $featureName(state: ViewState, onIntent: (Intent) -> Unit) {}
        |$preview
        """.trimMargin(),
      )
    }
  }

  File(commonMainPackageDir, "$viewStateName.kt").apply {
    if(!exists()) {
      createNewFile()
      writeText(
        """
        |package $packageName
        |
        |import androidx.compose.runtime.Immutable
        |
        |@Immutable
        |internal data object $viewStateName
        |
        """.trimMargin(),
      )
    }
  }

  if(shouldGeneratePreviewParameterProvider) {
    File(commonMainPackageDir, "$viewStatePreviewProviderName.kt").apply {
      if(!exists()) {
        createNewFile()
        writeText(
          """
          |@file:Suppress("ktlint:standard:argument-list-wrapping", "ktlint:standard:max-line-length")
          |
          |package $packageName
          |
          |import androidx.compose.ui.tooling.preview.PreviewParameterProvider
          |
          |internal class $viewStatePreviewProviderName : PreviewParameterProvider<ViewState> {
          |  override val values = sequenceOf(
          |    ViewState
          |  )
          |}
          |
          |internal typealias ViewStatePreviewProvider = $viewStatePreviewProviderName
          |
          """.trimMargin(),
        )
      }
    }
  }

  listOf(androidPackageDir, iosPackageDir, jsPackageDir, jvmPackageDir, wasmJsPackageDir).forEach { target ->
    val targetName = when(target) {
      androidPackageDir -> "android"
      iosPackageDir -> "ios"
      jsPackageDir -> "js"
      jvmPackageDir -> "jvm"
      wasmJsPackageDir -> "wasmjs"
      else -> error("unknown target source set $target")
    }

    File(target, "$portalName.$targetName.kt").apply {
      if(!exists()) {
        createNewFile()
        writeText(
          """
          |package $packageName
          |
          |import app.immich.kmp.core.ImmichSessionComponent
          |
          |internal actual fun $componentName.Companion.createA(
          |  sessionComponent: ImmichSessionComponent,
          |): $componentName = $componentName.Companion.create(sessionComponent)
          |
          """.trimMargin(),
        )
      }
    }
  }
}
