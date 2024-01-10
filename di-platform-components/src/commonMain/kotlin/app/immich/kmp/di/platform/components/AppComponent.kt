package app.immich.kmp.di.platform.components

import app.immich.kmp.di.scopes.AppSingleton
import me.tatarka.inject.annotations.Component

@AppSingleton
@Component
abstract class AppComponent(
  @Component protected val platformComponent: PlatformComponent,
) {
  companion object
}
