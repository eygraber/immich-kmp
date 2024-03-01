package com.eygraber.virtue.theme

import com.eygraber.virtue.di.scopes.AppSingleton
import me.tatarka.inject.annotations.Inject

@AppSingleton
@Inject
public actual class ThemeSettingStorage {
  public actual suspend fun load(): ThemeSetting? = TODO()

  public actual suspend fun store(setting: ThemeSetting) {
    TODO()
  }
}
