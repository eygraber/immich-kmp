package app.immich.kmp.features.root.album

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

internal typealias Route = RootRoute.Album
internal typealias View = AlbumView
internal typealias Intent = AlbumIntent
internal typealias Compositor = AlbumCompositor
internal typealias Effects = AlbumEffects
internal typealias ViewState = AlbumViewState

object AlbumPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = AlbumPortal(route, parentComponent)
}

internal class AlbumPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = AlbumComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class AlbumComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@KmpComponentCreate
internal expect fun AlbumComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): AlbumComponent
