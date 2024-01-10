package app.immich.kmp.ui.session

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.immich.kmp.di.scopes.UiSessionSingleton
import me.tatarka.inject.annotations.Inject

@UiSessionSingleton
@Inject
class UiSession(
  @Suppress("unused") private val uiSessionComponent: UiSessionComponent,
) {
  @Suppress("ModifierMissing")
  @Composable
  fun Main() {
    MaterialTheme(
      colorScheme = when {
        isSystemInDarkTheme() -> darkColorScheme(background = Color.Black)
        else -> lightColorScheme()
      },
    ) {
      Scaffold { contentPadding ->
        Box(
          modifier = Modifier.fillMaxSize().padding(contentPadding),
          contentAlignment = Alignment.Center,
        ) {
          Text(text = "Hello, world!")
        }
      }
    }
  }
}
