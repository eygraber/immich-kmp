package app.immich.kmp.features.main.map

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.Map
internal typealias View = MapView
internal typealias Intent = MapIntent
internal typealias Compositor = MapCompositor
internal typealias Effects = MapEffects
internal typealias ViewState = MapViewState

object MapPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = MapPortal(route, parentComponent)
}

internal class MapPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = MapComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class MapComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun MapComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): MapComponent
