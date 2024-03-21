package com.eygraber.virtue.theme

import com.eygraber.virtue.di.scopes.AppSingleton
import me.tatarka.inject.annotations.Inject

@AppSingleton
@Inject
public actual class ThemeSettingStorage {
  public actual suspend fun load(): ThemeSetting? = ThemeSetting.System

  public actual suspend fun store(setting: ThemeSetting) {
    // TODO: Set up an actual implementation for ThemeSettingStorage on ios
  }
}
