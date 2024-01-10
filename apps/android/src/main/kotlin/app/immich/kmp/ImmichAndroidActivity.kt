package app.immich.kmp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import app.immich.kmp.ui.session.UiSessionComponent
import app.immich.kmp.ui.session.create

class ImmichAndroidActivity : AppCompatActivity() {
  private val uiSessionComponent by lazy {
    val app = requireNotNull(applicationContext as? ImmichAndroidApplication) {
      "applicationContext must be a ImmichApplication"
    }

    UiSessionComponent.create(
      app.appComponent,
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()

    super.onCreate(savedInstanceState)

    setContent {
      SystemUiController()

      uiSessionComponent.uiSession.Main()
    }
  }
}

@Composable
private fun ComponentActivity.SystemUiController() {
  val isDarkTheme = isSystemInDarkTheme()

  DisposableEffect(isDarkTheme) {
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.auto(
        Color.TRANSPARENT,
        Color.TRANSPARENT,
      ) { isDarkTheme },
      navigationBarStyle = SystemBarStyle.auto(
        lightScrim,
        darkScrim,
      ) { isDarkTheme },
    )

    onDispose {}
  }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
