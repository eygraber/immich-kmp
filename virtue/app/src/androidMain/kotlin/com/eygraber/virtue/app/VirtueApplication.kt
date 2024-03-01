package com.eygraber.virtue.app

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.eygraber.virtue.config.VirtueConfig
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.theme.ThemeSetting

public interface VirtueApplication<A : AppComponent> {
  public val virtuePlatformComponent: VirtuePlatformComponent
  public val virtueAppComponent: VirtueAppComponent
  public val appComponent: A

  public val config: VirtueConfig

  public val darkColorScheme: ColorScheme get() = darkColorScheme()
  public val lightColorScheme: ColorScheme get() = lightColorScheme()

  public val defaultThemeSetting: ThemeSetting get() = ThemeSetting.System

  public suspend fun initialize() {
    virtueAppComponent.themeSettings.initialize(
      default = defaultThemeSetting,
    )
  }
}
