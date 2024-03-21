import app.immich.kmp.APP_NAME
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import app.immich.kmp.immichSessionPortalFactory
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.virtueApplication
import com.eygraber.virtue.config.WasmVirtueConfig

fun main() = virtueApplication(
  appComponentFactory = { virtueAppComponent ->
    ImmichAppComponent.create(
      virtueAppComponent = virtueAppComponent,
    )
  },
  initialSessionComponentFactory = { appComponent, virtuePlatformSessionComponent ->
    ImmichSessionComponent.create(
      appComponent = appComponent,
      virtuePlatformSessionComponent = virtuePlatformSessionComponent,
      portalFactory = immichSessionPortalFactory(),
    )
  },
  config = WasmVirtueConfig(
    appName = APP_NAME,
  ),
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
)
