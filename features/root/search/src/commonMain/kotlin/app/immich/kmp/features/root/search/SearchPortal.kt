package app.immich.kmp.features.root.search

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = RootRoute.Search
internal typealias View = SearchView
internal typealias Intent = SearchIntent
internal typealias Compositor = SearchCompositor
internal typealias Effects = SearchEffects
internal typealias ViewState = SearchViewState

object SearchPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = SearchPortal(route, parentComponent)
}

internal class SearchPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = SearchComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class SearchComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun SearchComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): SearchComponent
