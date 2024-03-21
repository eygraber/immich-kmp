package app.immich.kmp.router

import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.uri.Uri
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.session.VirtuePortalFactory
import com.eygraber.virtue.session.VirtuePortalRouter
import me.tatarka.inject.annotations.Inject

@SessionSingleton
@Inject
class ImmichRouter(
  private val portalFactory: VirtuePortalFactory<ImmichRoute>,
  portalManager: ComposePortalManager<ImmichRoute>,
) : VirtuePortalRouter<ImmichRoute>(
  portalManager,
) {
  override fun mapUriToPortal(uri: Uri) = when(val route = ImmichRoute.fromUri(uri)) {
    is AdminRoute -> portalFactory(HostRoute.Admin)
    is MainRoute -> portalFactory(HostRoute.Main)
    is RootRoute -> portalFactory(route)
    else -> portalFactory(RootRoute.FourOhFour(uri))
  }
}
