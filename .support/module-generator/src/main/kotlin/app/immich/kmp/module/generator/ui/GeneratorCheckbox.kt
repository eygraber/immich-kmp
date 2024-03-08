package app.immich.kmp.module.generator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun GeneratorCheckbox(
  isChecked: Boolean,
  title: String,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  isEnabled: Boolean = true,
) {
  Row(
    modifier = modifier,
  ) {
    Checkbox(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      enabled = isEnabled,
    )

    val color = when {
      isEnabled -> Color.Unspecified
      else -> LocalContentColor.current.copy(alpha = 0.38F)
    }

    Text(
      text = title,
      color = color,
      modifier = Modifier
        .padding(start = 4.dp)
        .align(Alignment.CenterVertically)
        .focusProperties {
          canFocus = false
        }
        .run {
          if(isEnabled) {
            clickable {
              onCheckedChange(!isChecked)
            }
          }
          else {
            this
          }
        },
    )
  }
}
