package app.immich.kmp

import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import app.immich.kmp.router.ImmichRoute
import com.eygraber.uri.Uri
import com.eygraber.virtue.app.VirtueAndroidActivity
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent

class ImmichAndroidActivity : VirtueAndroidActivity<ImmichAppComponent, ImmichSessionComponent>() {
  override val defaultUri: Uri = ImmichRoute.RootUri

  override fun createSessionComponent(
    appComponent: ImmichAppComponent,
    virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  ) = ImmichSessionComponent.create(
    appComponent = appComponent,
    virtuePlatformSessionComponent = virtuePlatformSessionComponent,
    portalFactory = immichSessionPortalFactory(),
  )
}
