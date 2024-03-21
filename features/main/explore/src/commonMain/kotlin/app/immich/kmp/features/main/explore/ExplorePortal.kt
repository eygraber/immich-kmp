package app.immich.kmp.features.main.explore

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.Explore
internal typealias View = ExploreView
internal typealias Intent = ExploreIntent
internal typealias Compositor = ExploreCompositor
internal typealias Effects = ExploreEffects
internal typealias ViewState = ExploreViewState

object ExplorePortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = ExplorePortal(route, parentComponent)
}

internal class ExplorePortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = ExploreComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class ExploreComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun ExploreComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ExploreComponent
