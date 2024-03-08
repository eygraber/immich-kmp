package app.immich.kmp.module.generator.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import kotlin.system.exitProcess

@Composable
internal fun GeneratorErrorDialog(
  text: String,
) {
  DialogWindow(
    onCloseRequest = { exitProcess(1) },
    state = rememberDialogState(
      size = DpSize(400.dp, 175.dp),
    ),
    undecorated = true,
    resizable = false,
  ) {
    MaterialTheme(
      colorScheme = if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
      Surface(
        tonalElevation = 6.dp,
      ) {
        Column(modifier = Modifier.padding(24.dp)) {
          Text(
            text = "Error",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleLarge,
          )

          Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
          )

          TextButton(
            onClick = { exitProcess(0) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
          ) {
            Text("OK")
          }
        }
      }
    }
  }
}
