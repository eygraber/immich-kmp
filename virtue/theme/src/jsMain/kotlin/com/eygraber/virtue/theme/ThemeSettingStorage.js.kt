package com.eygraber.virtue.theme

import com.eygraber.virtue.di.scopes.AppSingleton
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.WindowLocalStorage
import org.w3c.dom.get
import org.w3c.dom.set

@AppSingleton
@Inject
public actual class ThemeSettingStorage(
  windowLocalStorage: WindowLocalStorage,
) {
  private val localStorage = windowLocalStorage.localStorage

  public actual suspend fun load(): ThemeSetting? =
    localStorage[KEY]?.let { setting ->
      ThemeSetting.entries.find { it.name == setting }
    }

  public actual suspend fun store(setting: ThemeSetting) {
    localStorage[KEY] = setting.name
  }
}

private const val KEY = "com.eygraber.virtue.theme.ThemeSetting"
