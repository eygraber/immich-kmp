package com.eygraber.virtue.session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.eygraber.uri.Uri
import com.eygraber.virtue.back.press.dispatch.BackHandler
import com.eygraber.virtue.back.press.dispatch.PlatformNavigationHandler
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.history.History
import com.eygraber.virtue.theme.ThemeSettings
import com.eygraber.virtue.theme.compose.isApplicationInDarkTheme
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@SessionSingleton
@Inject
public class VirtueSession(
  private val history: History,
  private val router: VirtueRouter,
  private val themeSettings: ThemeSettings,
) {
  private var isBackHandlerEnabled by mutableStateOf(history.canGoBack)

  @Suppress("ModifierMissing")
  @Composable
  public fun SessionUi(
    darkColorScheme: ColorScheme,
    lightColorScheme: ColorScheme,
    defaultUri: Uri,
  ) {
    HistoryChangesEffect()

    BackHandler(enabled = isBackHandlerEnabled) {
      history.onBackPressed()
    }

    HistoryLifecycleEffect(
      defaultUri = defaultUri,
    )

    MaterialTheme(
      colorScheme = when {
        themeSettings.isApplicationInDarkTheme() -> darkColorScheme
        else -> lightColorScheme
      },
    ) {
      Scaffold { contentPadding ->
        PlatformNavigation()

        Box(
          modifier = Modifier.fillMaxSize().padding(contentPadding),
        ) {
          router.Render()
        }
      }
    }
  }

  @Composable
  private fun HistoryChangesEffect() {
    LaunchedEffect(history) {
      launch {
        history.changes.collect { currentItem ->
          isBackHandlerEnabled = history.canGoBack
          router.route(currentItem.payload.uri)
        }
      }
    }
  }

  @Composable
  private fun HistoryLifecycleEffect(defaultUri: Uri) {
    DisposableEffect(history, defaultUri) {
      history.initialize(defaultUri)

      onDispose {
        history.destroy()
      }
    }
  }

  @Composable
  private fun PlatformNavigation() {
    PlatformNavigationHandler(
      onForwardPress = {
        if(history.canGoForward) {
          history.moveForward()
        }
      },
    )
  }
}
