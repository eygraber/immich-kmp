package app.immich.kmp.features.main.favorites

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

internal typealias Route = MainRoute.Favorites
internal typealias View = FavoritesView
internal typealias Intent = FavoritesIntent
internal typealias Compositor = FavoritesCompositor
internal typealias Effects = FavoritesEffects
internal typealias ViewState = FavoritesViewState

object FavoritesPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = FavoritesPortal(route, parentComponent)
}

internal class FavoritesPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = FavoritesComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class FavoritesComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@KmpComponentCreate
internal expect fun FavoritesComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): FavoritesComponent
