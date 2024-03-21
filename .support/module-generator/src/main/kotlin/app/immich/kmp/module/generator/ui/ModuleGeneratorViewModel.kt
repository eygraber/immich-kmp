package app.immich.kmp.module.generator.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.immich.kmp.module.generator.addModuleToAppDependencies
import app.immich.kmp.module.generator.addModuleToSettings
import app.immich.kmp.module.generator.addRoute
import app.immich.kmp.module.generator.addToPortalFactory
import app.immich.kmp.module.generator.createModule
import app.immich.kmp.module.generator.utils.camelCaseToDotCase
import app.immich.kmp.module.generator.utils.camelCaseToKebabCase
import java.io.File
import kotlin.system.exitProcess

private const val PackageNamePrefix = "app.immich.kmp.features."

private val ModuleNameRegex = Regex("^([a-z]+(-))*[a-z]+(?::([a-z]+(-))*[a-z]+)*\$")
private val PackageNameRegex = Regex("^[a-z][a-z0-9_]*(\\.[a-z0-9_]+)*[a-z0-9_]*\$")
private val FeatureNameRegex = Regex("[A-Z][A-Za-z0-9]*")

internal enum class PortalType {
  Admin,
  Host,
  Main,
  Root,
}

internal class ModuleGeneratorViewModel {
  private val projectDir = File(System.getProperty("user.dir")).run {
    if(name == "module-generator") {
      parentFile.parentFile
    }
    else {
      this
    }
  }

  private val featuresDir = File(projectDir, "features")

  var isProjectDirValid = projectDir.name == "immich-kmp"
  var shouldInferModuleName by mutableStateOf(true)
  var shouldInferPackageName by mutableStateOf(true)
  var shouldGeneratePreview by mutableStateOf(true)

  // TODO: Default generating a PreviewParameterProvider to true when KMP supports it
  private var shouldGeneratePreviewParameterProviderInternal by mutableStateOf(false)
  val shouldGeneratePreviewParameterProvider by derivedStateOf {
    shouldGeneratePreviewParameterProviderInternal && shouldGeneratePreview
  }
  var featureName by mutableStateOf("")
  var featureNameError by mutableStateOf<String?>(null)
  var portalType by mutableStateOf(PortalType.Main)
  var doesModuleAlreadyExist by mutableStateOf(false)
  var moduleNamePrefix by mutableStateOf(":features:${portalType.name.lowercase()}:")
  var moduleName by mutableStateOf("")
  var moduleNameError by mutableStateOf<String?>(null)
  var packageName by mutableStateOf(PackageNamePrefix)
  var packageNameError by mutableStateOf<String?>(null)

  val isGenerationEnabled by derivedStateOf {
    moduleName.isNotEmpty() && moduleNameError == null &&
      packageName.isNotEmpty() && packageNameError == null &&
      featureName.isNotEmpty() && featureNameError == null
  }

  fun onInferPackageNameChange(newValue: Boolean) {
    shouldInferPackageName = newValue

    if(newValue) {
      onPackageNameChange(generateInferredPackageName())
    }
  }

  fun onInferModuleNameChange(newValue: Boolean) {
    shouldInferModuleName = newValue

    val portalTypeName = portalType.name.lowercase()
    updateModuleNamePrefix()
    if(newValue) {
      onModuleNameChange(generateInferredModuleName())
    }
    else {
      onModuleNameChange("$portalTypeName:${moduleName.removePrefix(portalTypeName).removePrefix(":")}")
    }
  }

  fun onGeneratePreviewChange(newValue: Boolean) {
    shouldGeneratePreview = newValue
  }

  fun onGeneratePreviewParameterProviderChange(newValue: Boolean) {
    shouldGeneratePreviewParameterProviderInternal = newValue
  }

  fun onFeatureNameChange(newFeatureName: String) {
    featureName = newFeatureName.trim()

    val isValid = featureName.matches(FeatureNameRegex)

    if(isValid) {
      if(shouldInferPackageName) {
        onPackageNameChange(generateInferredPackageName())
      }

      if(shouldInferModuleName) {
        onModuleNameChange(generateInferredModuleName())
      }
    }

    featureNameError = when {
      isValid -> null

      else -> when {
        featureName.isBlank() -> "Feature name must not be empty"

        else ->
          """
          |Feature name:
          |  • must begin with an uppercase character
          |  • can only contain characters or digits
          """.trimMargin()
      }
    }
  }

  fun onPortalTypeChange(newPortalType: PortalType) {
    portalType = newPortalType
    updateModuleNamePrefix()
    if(shouldInferPackageName) {
      onPackageNameChange(generateInferredPackageName())
    }
  }

  fun onModuleNameChange(newModuleName: String) {
    moduleName = newModuleName.trim()

    val isValid = moduleName.matches(ModuleNameRegex)

    doesModuleAlreadyExist = when {
      isValid -> with(File(featuresDir, "${portalType.name}/$moduleName")) {
        exists() && isDirectory
      }

      else -> false
    }

    moduleNameError = when {
      isValid -> null

      else -> when {
        moduleName.isBlank() -> "Module name must not be empty"

        else ->
          """
          |Module name:
          |  • must begin and end with a lowercase character
          |  • can't have consecutive '-'
          |  • can only contain lowercase characters and '-'
          """.trimMargin()
      }
    }
  }

  fun onPackageNameChange(newPackageName: String) {
    packageName = newPackageName.trim()

    val isValid = packageName.matches(PackageNameRegex)

    packageNameError = when {
      isValid -> null

      else -> when {
        packageName.isBlank() -> "Package name must not be empty"

        else ->
          """
          |Package name:
          |  • must begin with a lowercase character
          |  • can only contain lowercase characters, digits, '.', and '_'
          |  • can't have consecutive '.'
          |  • can't end with a '.'
          """.trimMargin()
      }
    }
  }

  fun generate() {
    createModule(
      projectDir = projectDir,
      moduleName = moduleName,
      packageName = packageName,
      featureName = featureName,
      portalType = portalType,
      shouldGeneratePreview = shouldGeneratePreview,
      shouldGeneratePreviewParameterProvider = shouldGeneratePreviewParameterProviderInternal,
    )

    addModuleToSettings(
      projectDir = projectDir,
      moduleName = moduleName,
    )

    if(addModuleToAppDependencies(projectDir, moduleName)) {
      addToPortalFactory(
        projectDir = projectDir,
        packageName = packageName,
        portalType = portalType,
        portalName = featureName,
      )
    }

    addRoute(
      projectDir = projectDir,
      portalType = portalType,
      featureName = featureName,
    )

    exitProcess(0)
  }

  private fun generateInferredPackageName() =
    "$PackageNamePrefix${portalType.name.lowercase()}.${featureName.camelCaseToDotCase()}"

  private fun generateInferredModuleName() = featureName.camelCaseToKebabCase()

  private fun updateModuleNamePrefix() {
    moduleNamePrefix = when {
      shouldInferModuleName -> ":features:${portalType.name.lowercase()}:"
      else -> ":features:"
    }
  }
}
