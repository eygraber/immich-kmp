package app.immich.kmp

import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import com.eygraber.virtue.app.VirtueAndroidActivity
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent

class ImmichAndroidActivity : VirtueAndroidActivity<ImmichAppComponent, ImmichSessionComponent>() {
  override fun createSessionComponent(
    appComponent: ImmichAppComponent,
    virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  ) = ImmichSessionComponent.create(
    appComponent = appComponent,
    virtuePlatformSessionComponent = virtuePlatformSessionComponent,
    screenFactory = immichSessionScreenFactory(),
  )
}
