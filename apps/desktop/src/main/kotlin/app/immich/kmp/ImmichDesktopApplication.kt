package app.immich.kmp

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.virtueApplication
import com.eygraber.virtue.config.DesktopVirtueConfig
import java.io.File

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
  config = DesktopVirtueConfig(
    appName = APP_NAME,
    configDir = File("/tmp/immich").apply {
      mkdirs()
    },
  ),
  configureInitialSessionParams = { params ->
    params.copy(
      minWindowSize = DpSize(400.dp, 400.dp),
    )
  },
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
  defaultUri = ImmichRoute.RootUri,
)
