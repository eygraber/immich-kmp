package com.eygraber.virtue.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.components.create
import com.eygraber.virtue.session.GenericVirtueSessionComponent
import com.eygraber.virtue.theme.compose.isApplicationInDarkTheme

public abstract class VirtueAndroidActivity<A, out S> : ComponentActivity()
  where A : AppComponent, S : GenericVirtueSessionComponent {
  protected open val isEdgeToEdge: Boolean = true

  private val virtueApp: VirtueApplication<A> by lazy(LazyThreadSafetyMode.NONE) {
    requireNotNull(
      runCatching {
        @Suppress("UNCHECKED_CAST")
        applicationContext as? VirtueApplication<A>
      }.getOrNull(),
    ) {
      "applicationContext must be a ${VirtueApplication::class.simpleName} typed to the same A as VirtueAndroidActivity"
    }
  }

  protected val appComponent: A by lazy(LazyThreadSafetyMode.NONE) {
    virtueApp.appComponent
  }

  protected val virtuePlatformSessionComponent: VirtuePlatformSessionComponent by lazy(LazyThreadSafetyMode.NONE) {
    VirtuePlatformSessionComponent.create(
      context = this,
    )
  }

  protected val virtueSessionComponent: S by lazy(LazyThreadSafetyMode.NONE) {
    createSessionComponent(appComponent, virtuePlatformSessionComponent)
  }

  protected abstract fun createSessionComponent(
    appComponent: A,
    virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  ): S

  override fun onCreate(savedInstanceState: Bundle?) {
    savedInstanceState?.let(virtueSessionComponent.stateManager::onRestoreState)

    if(isEdgeToEdge) {
      enableEdgeToEdge()
    }

    super.onCreate(savedInstanceState)

    setContent {
      if(isEdgeToEdge) {
        SystemUiController(
          isDarkTheme = appComponent.virtueAppComponent.themeSettings.isApplicationInDarkTheme(),
        )
      }

      virtueSessionComponent.session.SessionUi(
        darkColorScheme = virtueApp.darkColorScheme,
        lightColorScheme = virtueApp.lightColorScheme,
      )
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    virtueSessionComponent.stateManager.onSaveState(outState)
  }
}

@Composable
private fun ComponentActivity.SystemUiController(
  isDarkTheme: Boolean,
) {
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
