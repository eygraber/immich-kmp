package com.eygraber.virtue.di.components

import com.eygraber.virtue.config.VirtueConfig
import com.eygraber.virtue.di.scopes.AppSingleton
import com.eygraber.virtue.theme.ThemeSettings
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppSingleton
@Component
public abstract class VirtueAppComponent(
  @Component public val platformComponent: VirtuePlatformComponent,
  @get:Provides public val config: VirtueConfig,
) {
  public abstract val themeSettings: ThemeSettings

  public companion object
}
