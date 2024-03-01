package app.immich.kmp.core

import app.immich.kmp.router.ImmichNav
import app.immich.kmp.router.ImmichSessionRouter
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.session.GenericVirtueScreen
import com.eygraber.virtue.session.VirtueSessionComponent
import com.eygraber.virtue.session.VirtueSessionRouter
import me.tatarka.inject.annotations.Component

@Component
abstract class ImmichSessionComponent(
  @Component override val appComponent: ImmichAppComponent,
  @Component override val virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  screenFactory: (ImmichSessionComponent, ImmichNav) -> GenericVirtueScreen<out ImmichNav>,
) : VirtueSessionComponent<ImmichSessionComponent, ImmichNav, ImmichSessionRouter>(screenFactory) {
  override fun ImmichSessionRouter.bind(): VirtueSessionRouter = this

  companion object
}
