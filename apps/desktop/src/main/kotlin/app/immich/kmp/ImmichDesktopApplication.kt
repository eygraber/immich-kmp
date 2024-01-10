package app.immich.kmp

import androidx.compose.ui.window.singleWindowApplication
import app.immich.kmp.di.platform.components.AppComponent
import app.immich.kmp.di.platform.components.PlatformComponent
import app.immich.kmp.di.platform.components.create
import app.immich.kmp.ui.session.UiSessionComponent
import app.immich.kmp.ui.session.create

fun main() {
  val appComponent = AppComponent.create(
    platformComponent = PlatformComponent.create(),
  )

  val uiSessionComponent = UiSessionComponent.create(
    appComponent,
  )

  singleWindowApplication {
    uiSessionComponent.uiSession.Main()
  }
}
