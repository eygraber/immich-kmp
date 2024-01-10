package app.immich.kmp

import android.app.Application
import app.immich.kmp.di.platform.components.AndroidComponent
import app.immich.kmp.di.platform.components.AndroidSystemServiceComponent
import app.immich.kmp.di.platform.components.AppComponent
import app.immich.kmp.di.platform.components.PlatformComponent
import app.immich.kmp.di.platform.components.create

class ImmichAndroidApplication : Application() {
  private val androidComponent by lazy {
    AndroidComponent.create(applicationContext)
  }

  private val androidSystemServiceComponent by lazy {
    AndroidSystemServiceComponent.create(androidComponent)
  }

  private val platformComponent by lazy {
    PlatformComponent.create(
      systemServiceComponent = androidSystemServiceComponent,
    )
  }

  internal val appComponent by lazy {
    AppComponent.create(
      platformComponent = platformComponent,
    )
  }
}
