package com.eygraber.virtue.app

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.window.ComposeUIViewController
import com.eygraber.uri.Uri
import com.eygraber.virtue.back.press.dispatch.WithBackPressDispatching
import com.eygraber.virtue.config.IosVirtueConfig
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.components.createKmp
import com.eygraber.virtue.session.BaseVirtueSessionComponent
import com.eygraber.virtue.session.GenericVirtueSessionComponent
import com.eygraber.virtue.theme.ThemeSetting
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import platform.UIKit.UIViewController

public abstract class VirtueApplication<A : AppComponent>(
  private val config: IosVirtueConfig,
  private val darkColorScheme: ColorScheme = darkColorScheme(),
  private val lightColorScheme: ColorScheme = lightColorScheme(),
  private val defaultThemeSetting: ThemeSetting = ThemeSetting.System,
) {
  protected val virtuePlatformComponent: VirtuePlatformComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtuePlatformComponent.createKmp()
  }

  protected val virtueAppComponent: VirtueAppComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtueAppComponent.createKmp(
      platformComponent = virtuePlatformComponent,
      config = config,
    )
  }

  protected abstract val appComponent: A

  protected abstract fun createSessionComponent(
    appComponent: A,
    virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  ): GenericVirtueSessionComponent

  public abstract fun createViewController(): UIViewController

  init {
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch {
      virtueAppComponent.themeSettings.initialize(
        default = defaultThemeSetting,
      )
    }
  }

  protected fun createVirtueViewController(
    defaultUri: Uri,
  ): UIViewController {
    val virtuePlatformSessionComponent = VirtuePlatformSessionComponent.createKmp()

    val sessionComponent = createSessionComponent(
      appComponent = appComponent,
      virtuePlatformSessionComponent = virtuePlatformSessionComponent,
    ) as BaseVirtueSessionComponent

    return ComposeUIViewController {
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
