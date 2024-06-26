package com.eygraber.virtue.app

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.eygraber.uri.Uri
import com.eygraber.virtue.back.press.dispatch.WithBackPressDispatching
import com.eygraber.virtue.config.DesktopVirtueConfig
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.components.create
import com.eygraber.virtue.session.BaseVirtueSessionComponent
import com.eygraber.virtue.session.GenericVirtueSessionComponent
import com.eygraber.virtue.session.VirtueSessionParams
import com.eygraber.virtue.theme.ThemeSetting
import java.awt.Dimension

public fun <A : AppComponent, S : GenericVirtueSessionComponent> virtueApplication(
  appComponentFactory: (VirtueAppComponent) -> A,
  initialSessionComponentFactory: (A, VirtuePlatformSessionComponent) -> S,
  config: DesktopVirtueConfig,
  defaultUri: Uri,
  configureInitialSessionParams: (VirtueSessionParams) -> VirtueSessionParams = { it },
  onAllSessionsClosed: ApplicationScope.() -> Unit = { exitApplication() },
  darkColorScheme: ColorScheme = darkColorScheme(),
  lightColorScheme: ColorScheme = lightColorScheme(),
  defaultThemeSetting: ThemeSetting = ThemeSetting.System,
) {
  val virtuePlatformComponent: VirtuePlatformComponent =
    VirtuePlatformComponent.create()

  val virtueAppComponent: VirtueAppComponent =
    VirtueAppComponent.create(
      platformComponent = virtuePlatformComponent,
      config = config,
    )

  val virtuePlatformSessionComponent = VirtuePlatformSessionComponent.create()

  val appComponent = appComponentFactory(virtueAppComponent)

  val initialSessionComponent = initialSessionComponentFactory(
    appComponent,
    virtuePlatformSessionComponent,
  ) as BaseVirtueSessionComponent

  val initialSessionParams = configureInitialSessionParams(
    VirtueSessionParams(
      title = config.appName,
    ),
  )

  val sessionManager = initialSessionComponent.sessionManager.apply {
    addSession(initialSessionComponent, initialSessionParams)
  }

  application {
    InitializationEffect(
      themeSettings = appComponent.virtueAppComponent.themeSettings,
      defaultThemeSetting = defaultThemeSetting,
    )

    val sessions by sessionManager.sessions.collectAsState()
    for(sessionEntry in sessions) {
      val sessionComponent = sessionEntry.sessionComponent
      val params = sessionEntry.params
      key(sessionComponent) {
        Window(
          onCloseRequest = {
            if(sessions.size == 1) {
              onAllSessionsClosed()
            }
            else {
              sessionManager.removeSession(sessionComponent)
            }
          },
          state = params.windowState,
          visible = params.visible,
          title = params.title,
          icon = params.icon,
          undecorated = params.undecorated,
          transparent = params.transparent,
          resizable = params.resizable,
          enabled = params.enabled,
          focusable = params.focusable,
          alwaysOnTop = params.alwaysOnTop,
          onPreviewKeyEvent = params.onPreviewKeyEvent,
          onKeyEvent = params.onKeyEvent,
        ) {
          WindowMinSizeEffect(
            params = params,
            window = window,
          )

          WithBackPressDispatching(
            onBackPressedDispatcher = sessionComponent.onBackPressedDispatcher,
          ) {
            sessionComponent.session.SessionUi(
              darkColorScheme = darkColorScheme,
              lightColorScheme = lightColorScheme,
              defaultUri = defaultUri,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun WindowMinSizeEffect(
  window: ComposeWindow,
  params: VirtueSessionParams,
) {
  val minimumWindowSize = when(val minSize = params.minWindowSize) {
    null -> null
    else -> with(LocalDensity.current) {
      remember(density, minSize) { Dimension(minSize.width.roundToPx(), minSize.height.roundToPx()) }
    }
  }

  DisposableEffect(window, minimumWindowSize) {
    if(minimumWindowSize != null) {
      window.minimumSize = minimumWindowSize
    }

    onDispose {}
  }
}
