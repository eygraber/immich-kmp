package com.eygraber.virtue.app

import android.app.Application
import com.eygraber.virtue.config.AndroidVirtueConfig
import com.eygraber.virtue.di.components.AndroidComponent
import com.eygraber.virtue.di.components.AndroidSystemServiceComponent
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import com.eygraber.virtue.di.components.VirtuePlatformComponent
import com.eygraber.virtue.di.components.create
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

public abstract class VirtueAndroidApplication<A : AppComponent> : Application(), VirtueApplication<A> {
  private val androidComponent: AndroidComponent by lazy(LazyThreadSafetyMode.NONE) {
    AndroidComponent.create(applicationContext)
  }

  private val androidSystemServiceComponent: AndroidSystemServiceComponent by lazy(LazyThreadSafetyMode.NONE) {
    AndroidSystemServiceComponent.create(androidComponent)
  }

  final override val virtuePlatformComponent: VirtuePlatformComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtuePlatformComponent.create(
      systemServiceComponent = androidSystemServiceComponent,
    )
  }

  abstract override val config: AndroidVirtueConfig

  final override val virtueAppComponent: VirtueAppComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtueAppComponent.create(
      platformComponent = virtuePlatformComponent,
      config = config,
    )
  }

  final override val appComponent: A by lazy(LazyThreadSafetyMode.NONE) {
    createAppComponent()
  }

  protected abstract fun createAppComponent(): A

  override fun onCreate() {
    super.onCreate()

    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch {
      initialize()
    }
  }
}
