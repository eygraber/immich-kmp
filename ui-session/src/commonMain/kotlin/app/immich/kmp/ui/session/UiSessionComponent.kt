package app.immich.kmp.ui.session

import app.immich.kmp.di.platform.components.AppComponent
import app.immich.kmp.di.scopes.UiSessionSingleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@UiSessionSingleton
@Component
abstract class UiSessionComponent(
  @Component protected val appComponent: AppComponent,
) {
  @Provides fun provideUiSessionComponent(): UiSessionComponent = this

  abstract val uiSession: UiSession

  companion object
}
