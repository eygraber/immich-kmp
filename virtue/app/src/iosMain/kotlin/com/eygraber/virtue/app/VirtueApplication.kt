package com.eygraber.virtue.app

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.eygraber.virtue.config.VirtueConfig
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.di.components.createKmp
import com.eygraber.virtue.theme.ThemeSetting
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

public interface VirtueApplication<A : AppComponent> {
  public val model: VirtueApplicationModel<A>

  public fun initialize() {
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch {
      model.initialize()
    }
  }
}

public class VirtueApplicationModel<A : AppComponent>(
  public val createAppComponent: VirtueApplicationModel<A>.() -> A,
  public val config: VirtueConfig,
  public val darkColorScheme: ColorScheme = darkColorScheme(),
  public val lightColorScheme: ColorScheme = lightColorScheme(),
  public val defaultThemeSetting: ThemeSetting = ThemeSetting.System,
) {
  public val virtuePlatformComponent: VirtuePlatformComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtuePlatformComponent.createKmp()
  }

  public val virtueAppComponent: VirtueAppComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtueAppComponent.createKmp(
      platformComponent = virtuePlatformComponent,
      config = config,
    )
  }

  public val appComponent: A by lazy(LazyThreadSafetyMode.NONE) {
    createAppComponent()
  }

  public suspend fun initialize() {
    virtueAppComponent.themeSettings.initialize(
      default = defaultThemeSetting,
    )
  }
}
