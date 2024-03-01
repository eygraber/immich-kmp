package com.eygraber.virtue.theme

import com.eygraber.virtue.di.scopes.AppSingleton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import kotlin.concurrent.Volatile

@AppSingleton
@Inject
public class ThemeSettings(
  private val storage: ThemeSettingStorage,
) {
  private val mutableSetting = MutableStateFlow(ThemeSetting.System)
  public val setting: StateFlow<ThemeSetting> = mutableSetting

  @Volatile private var default: ThemeSetting = ThemeSetting.System
  @Volatile private var isLoaded: Boolean = false

  init {
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch {
      mutableSetting.value = default
      val storedSetting = storage.load()
      if(storedSetting != null) {
        isLoaded = true
        mutableSetting.value = storedSetting
      }
    }
  }

  /**
   * Sets the default [ThemeSetting] that will be used if none is stored.
   *
   * Calling [setDefault] after a non-null stored setting is loaded is a no-op.
   *
   * @return `true` if the default was applied (i.e. a non-null stored setting hasn't been loaded yet);
   * otherwise `false`.
   */
  public fun setDefault(default: ThemeSetting = ThemeSetting.System): Boolean {
    if(!isLoaded) {
      mutableSetting.value = default
    }

    return !isLoaded
  }

  public suspend fun update(setting: ThemeSetting) {
    isLoaded = true
    mutableSetting.value = setting
    storage.store(setting)
  }
}
