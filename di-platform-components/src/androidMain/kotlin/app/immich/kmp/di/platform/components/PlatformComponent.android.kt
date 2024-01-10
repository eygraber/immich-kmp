package app.immich.kmp.di.platform.components

import me.tatarka.inject.annotations.Component

@Component
actual abstract class PlatformComponent(
  @Component val systemServiceComponent: AndroidSystemServiceComponent,
) {

  actual companion object
}
