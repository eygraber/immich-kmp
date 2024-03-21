package com.eygraber.virtue.app

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.eygraber.uri.Url
import com.eygraber.virtue.back.press.dispatch.WithBackPressDispatching
import com.eygraber.virtue.config.WasmVirtueConfig
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.components.VirtueWebPlatformComponent
import com.eygraber.virtue.di.components.create
import com.eygraber.virtue.session.BaseVirtueSessionComponent
import com.eygraber.virtue.session.GenericVirtueSessionComponent
import com.eygraber.virtue.theme.ThemeSetting

@OptIn(ExperimentalComposeUiApi::class)
public fun <A : AppComponent, S : GenericVirtueSessionComponent> virtueApplication(
  appComponentFactory: (VirtueAppComponent) -> A,
  initialSessionComponentFactory: (A, VirtuePlatformSessionComponent) -> S,
  config: WasmVirtueConfig,
  title: String = config.appName,
  darkColorScheme: ColorScheme = darkColorScheme(),
  lightColorScheme: ColorScheme = lightColorScheme(),
  defaultThemeSetting: ThemeSetting = ThemeSetting.System,
) {
  val webPlatformComponent = VirtueWebPlatformComponent.create()

  val virtuePlatformComponent: VirtuePlatformComponent =
    VirtuePlatformComponent.create(
      webComponent = webPlatformComponent,
    )

  val virtueAppComponent: VirtueAppComponent =
    VirtueAppComponent.create(
      platformComponent = virtuePlatformComponent,
      config = config,
    )

  val virtuePlatformSessionComponent = VirtuePlatformSessionComponent.create()

  val appComponent = appComponentFactory(virtueAppComponent)

  val sessionComponent = initialSessionComponentFactory(
    appComponent,
    virtuePlatformSessionComponent,
  ) as BaseVirtueSessionComponent

  CanvasBasedWindow(title) {
    InitializationEffect(
      themeSettings = appComponent.virtueAppComponent.themeSettings,
      defaultThemeSetting = defaultThemeSetting,
    )

    WithBackPressDispatching(
      onBackPressedDispatcher = sessionComponent.onBackPressedDispatcher,
    ) {
      sessionComponent.session.SessionUi(
        darkColorScheme = darkColorScheme,
        lightColorScheme = lightColorScheme,
        defaultUri = Url.parse(webPlatformComponent.browserLocation.href),
      )
    }
  }
}
