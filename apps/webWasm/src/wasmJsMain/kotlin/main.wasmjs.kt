import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import app.immich.kmp.di.platform.components.AppComponent
import app.immich.kmp.di.platform.components.PlatformComponent
import app.immich.kmp.di.platform.components.create
import app.immich.kmp.ui.session.UiSessionComponent
import app.immich.kmp.ui.session.create

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  val appComponent = AppComponent.create(
    platformComponent = PlatformComponent.create(),
  )

  val uiSessionComponent = UiSessionComponent.create(
    appComponent,
  )

  CanvasBasedWindow("Immich KMP") {
    uiSessionComponent.uiSession.Main()
  }
}
