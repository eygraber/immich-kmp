package app.immich.kmp.core

import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtueAppComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class ImmichAppComponent(
  @Component override val virtueAppComponent: VirtueAppComponent,
) : AppComponent {
  companion object
}
