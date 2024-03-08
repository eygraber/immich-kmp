package app.immich.kmp.module.generator.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

@Composable
fun GeneratorTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  error: String? = null,
  enabled: Boolean = true,
  prefix: String? = null,
) {
  val prefixStyle = SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5F))
  var selection by remember { mutableStateOf(TextRange.Zero) }

  OutlinedTextField(
    value = TextFieldValue(
      value,
      selection,
    ),
    onValueChange = { textValue ->
      if(selection != textValue.selection) selection = textValue.selection

      val text = textValue.text
      onValueChange(text)
    },
    modifier = modifier,
    label = {
      label()
    },
    isError = error != null,
    supportingText = when(error) {
      null -> null
      else -> {
        { Text(text = error) }
      }
    },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    singleLine = true,
    enabled = enabled,
    visualTransformation = when(prefix) {
      null -> VisualTransformation.None
      else -> VisualTransformation { text ->
        val transformedText = buildAnnotatedString {
          when {
            text.isEmpty() -> append("")

            else -> {
              withStyle(prefixStyle) {
                append(prefix)
              }

              append(text)
            }
          }
        }
        TransformedText(
          text = transformedText,
          offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int) = when {
              text.isEmpty() -> 0
              else -> offset + prefix.length
            }

            override fun transformedToOriginal(offset: Int) = when {
              offset <= prefix.lastIndex -> 0
              else -> offset - prefix.length
            }
          },
        )
      }
    },
  )
}
