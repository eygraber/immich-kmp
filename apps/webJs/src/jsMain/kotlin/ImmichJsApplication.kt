import app.immich.kmp.APP_NAME
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import app.immich.kmp.immichSessionScreenFactory
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.virtueApplication
import com.eygraber.virtue.config.JsVirtueConfig

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
      screenFactory = immichSessionScreenFactory(),
    )
  },
  config = JsVirtueConfig(
    appName = APP_NAME,
  ),
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
)
