package app.immich.kmp.core

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.router.ImmichRouter
import com.eygraber.portal.PortalRendererState
import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.portal.compose.PortalTransition
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.session.GenericVirtuePortal
import com.eygraber.virtue.session.VirtueRouter
import com.eygraber.virtue.session.VirtueSessionComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class ImmichSessionComponent(
  @Component override val appComponent: ImmichAppComponent,
  @Component override val virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  portalFactory: (ImmichSessionComponent, ImmichRoute) -> GenericVirtuePortal<out ImmichRoute>,
) : VirtueSessionComponent<ImmichSessionComponent, ImmichRoute, ImmichRouter>(portalFactory) {
  override fun ImmichRouter.bind(): VirtueRouter = this

  override fun createPortalManager(): ComposePortalManager<ImmichRoute> =
    ComposePortalManager(
      defaultTransitionProvider = { rendererState, _ ->
        when(rendererState) {
          PortalRendererState.Added, PortalRendererState.Attached -> PortalTransition.enter(fadeIn())
          PortalRendererState.Detached, PortalRendererState.Removed -> PortalTransition.exit(fadeOut())
        }
      },
    )

  companion object
}
