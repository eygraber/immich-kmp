package app.immich.kmp.module.generator.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import javax.swing.UIManager

fun main() {
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

  singleWindowApplication(title = "Module Generator") {
    val viewModel = remember { ModuleGeneratorViewModel() }

    MaterialTheme(
      colorScheme = if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
      Surface(modifier = Modifier.fillMaxSize()) {
        if(viewModel.isProjectDirValid) {
          Generator(viewModel)
        }
        else {
          GeneratorErrorDialog(
            text = "Please run the generator in the root of the project.",
          )
        }
      }
    }
  }
}

@Composable
private fun Generator(viewModel: ModuleGeneratorViewModel) {
  val initialFocusRequester = remember { FocusRequester() }

  LaunchedEffect(Unit) {
    initialFocusRequester.requestFocus()
  }

  Column(
    modifier = Modifier.padding(horizontal = 16.dp).verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Row {
      GeneratorCheckbox(
        isChecked = viewModel.shouldInferPackageNameFromModuleName,
        title = "Infer package name from module name",
        onCheckedChange = viewModel::onInferPackageNameFromModuleNameChange,
      )

      GeneratorCheckbox(
        isChecked = viewModel.shouldInferFeatureNameFromModuleName,
        title = "Infer feature name from module name",
        onCheckedChange = viewModel::onInferFeatureNameFromModuleNameChange,
      )
    }

    Row {
      GeneratorCheckbox(
        isChecked = viewModel.shouldGeneratePreview,
        title = "Generate a preview",
        onCheckedChange = viewModel::onGeneratePreviewChange,
      )

      GeneratorCheckbox(
        isChecked = viewModel.shouldGeneratePreviewParameterProvider,
        title = "Generate a preview parameter provider (not currently supported in KMP)",
        isEnabled = false, // viewModel.shouldGeneratePreview,
        onCheckedChange = viewModel::onGeneratePreviewParameterProviderChange,
      )
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      ModuleNameTextField(
        moduleName = viewModel.moduleName,
        moduleNameError = viewModel.moduleNameError,
        onValueChange = viewModel::onModuleNameChange,
        modifier = Modifier.focusRequester(initialFocusRequester),
      )

      if(viewModel.doesModuleAlreadyExist) {
        Text(
          text = "Generating into an existing module",
          color = MaterialTheme.colorScheme.primary,
          style = MaterialTheme.typography.labelMedium,
        )
      }
    }

    PackageNameTextField(
      packageName = viewModel.packageName,
      packageNameError = viewModel.packageNameError,
      isPackageEnabled = !viewModel.shouldInferPackageNameFromModuleName,
      onValueChange = viewModel::onPackageNameChange,
    )

    FeatureNameTextField(
      featureName = viewModel.featureName,
      featureNameError = viewModel.featureNameError,
      isFeatureEnabled = !viewModel.shouldInferFeatureNameFromModuleName,
      onValueChange = viewModel::onFeatureNameChange,
    )

    PortalTypeDropdownMenu(
      selectedType = viewModel.portalType,
      onTypeSelected = viewModel::onPortalTypeChange,
    )

    Button(
      onClick = { viewModel.generate() },
      enabled = viewModel.isGenerationEnabled,
    ) {
      Text(
        text = "Generate Module",
      )
    }
  }
}

@Composable
private fun ModuleNameTextField(
  moduleName: String,
  moduleNameError: String?,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  GeneratorTextField(
    value = moduleName,
    error = moduleNameError,
    label = {
      Text("Module name:")
    },
    onValueChange = onValueChange,
    modifier = modifier,
  )
}

@Composable
private fun PackageNameTextField(
  packageName: String,
  packageNameError: String?,
  isPackageEnabled: Boolean,
  onValueChange: (String) -> Unit,
) {
  GeneratorTextField(
    value = packageName,
    error = packageNameError,
    enabled = isPackageEnabled,
    label = {
      Text("Package name:")
    },
    onValueChange = onValueChange,
  )
}

@Composable
private fun FeatureNameTextField(
  featureName: String,
  featureNameError: String?,
  isFeatureEnabled: Boolean,
  onValueChange: (String) -> Unit,
) {
  GeneratorTextField(
    value = featureName,
    error = featureNameError,
    enabled = isFeatureEnabled,
    label = {
      Text("Feature name:")
    },
    onValueChange = onValueChange,
  )
}

@Composable
private fun PortalTypeDropdownMenu(
  selectedType: PortalType,
  onTypeSelected: (PortalType) -> Unit,
) {
  Row(
    modifier = Modifier.padding(top = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text("Nav type:", modifier = Modifier.alignByBaseline())

    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.alignByBaseline()) {
      TextButton(
        onClick = { isExpanded = !isExpanded },
      ) {
        Text(
          text = selectedType.name,
        )

        Icon(ExpandMoreIcon, contentDescription = "Expand More")
      }

      DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = {
          isExpanded = false
        },
      ) {
        for(type in PortalType.entries) {
          DropdownMenuItem(
            text = { Text(type.name) },
            onClick = {
              onTypeSelected(type)
              isExpanded = false
            },
          )
        }
      }
    }
  }
}

private val ExpandMoreIcon: ImageVector by lazy {
  materialIcon(name = "Filled.ExpandMore") {
    materialPath {
      moveTo(16.59f, 8.59f)
      lineTo(12.0f, 13.17f)
      lineTo(7.41f, 8.59f)
      lineTo(6.0f, 10.0f)
      lineToRelative(6.0f, 6.0f)
      lineToRelative(6.0f, -6.0f)
      close()
    }
  }
}
