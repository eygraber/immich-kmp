package app.immich.kmp

import androidx.compose.material3.ColorScheme
import app.immich.kmp.core.ImmichAppComponent
import app.immich.kmp.core.create
import app.immich.kmp.theme.ImmichTheme
import com.eygraber.virtue.app.VirtueAndroidApplication
import com.eygraber.virtue.config.AndroidVirtueConfig

class ImmichAndroidApplication : VirtueAndroidApplication<ImmichAppComponent>() {
  override fun createAppComponent(): ImmichAppComponent =
    ImmichAppComponent.create(
      virtueAppComponent = virtueAppComponent,
    )

  override val config = AndroidVirtueConfig(
    appName = APP_NAME,
  )

  override val darkColorScheme: ColorScheme = ImmichTheme.darkColorScheme
  override val lightColorScheme: ColorScheme = ImmichTheme.lightColorScheme
}
