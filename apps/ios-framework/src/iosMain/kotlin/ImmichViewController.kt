@file:Suppress("MissingPackageDeclaration")

import app.immich.kmp.APP_NAME
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.createKmp
import app.immich.kmp.immichSessionPortalFactory
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.VirtueApplicationModel
import com.eygraber.virtue.app.virtueViewController
import com.eygraber.virtue.config.IosVirtueConfig

fun immichApplicationModel() = VirtueApplicationModel(
  createAppComponent = {
    ImmichAppComponent.createKmp(
      virtueAppComponent = virtueAppComponent,
    )
  },
  config = IosVirtueConfig(
    appName = APP_NAME,
  ),
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
)

fun immichViewController(
  model: VirtueApplicationModel<ImmichAppComponent>,
) = virtueViewController(
  model = model,
  sessionComponentFactory = { appComponent, virtuePlatformSessionComponent ->
    ImmichSessionComponent.createKmp(
      appComponent = appComponent,
      virtuePlatformSessionComponent = virtuePlatformSessionComponent,
      portalFactory = immichSessionPortalFactory(),
    )
  },
  defaultUri = ImmichRoute.RootUri,
)
