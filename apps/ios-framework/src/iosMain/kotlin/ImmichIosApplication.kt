@file:Suppress("MissingPackageDeclaration")

import app.immich.kmp.APP_NAME
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.createKmp
import app.immich.kmp.immichSessionPortalFactory
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.VirtueApplication
import com.eygraber.virtue.config.IosVirtueConfig
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.session.GenericVirtueSessionComponent

class ImmichIosApplication : VirtueApplication<ImmichAppComponent>(
  config = IosVirtueConfig(
    appName = APP_NAME,
  ),
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
) {
  override val appComponent: ImmichAppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
    ImmichAppComponent.createKmp(
      virtueAppComponent = virtueAppComponent,
    )
  }

  override fun createSessionComponent(
    appComponent: ImmichAppComponent,
    virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  ): GenericVirtueSessionComponent = ImmichSessionComponent.createKmp(
    appComponent = appComponent,
    virtuePlatformSessionComponent = virtuePlatformSessionComponent,
    portalFactory = immichSessionPortalFactory(),
  )

  override fun createViewController() = createVirtueViewController(
    defaultUri = ImmichRoute.RootUri,
  )
}
