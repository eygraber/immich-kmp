package app.immich.kmp

import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.create
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.virtueApplication
import com.eygraber.virtue.config.DesktopVirtueConfig
import com.eygraber.virtue.di.components.create
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
      screenFactory = immichSessionScreenFactory(),
    )
  },
  config = DesktopVirtueConfig(
    appName = APP_NAME,
    configDir = File("/tmp/immich").apply {
      mkdirs()
    },
  ),
  darkColorScheme = ImmichTheme.darkColorScheme,
  lightColorScheme = ImmichTheme.lightColorScheme,
)
