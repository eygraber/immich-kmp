package app.immich.kmp.features.main.places

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

internal typealias Route = MainRoute.Places
internal typealias View = PlacesView
internal typealias Intent = PlacesIntent
internal typealias Compositor = PlacesCompositor
internal typealias Effects = PlacesEffects
internal typealias ViewState = PlacesViewState

object PlacesPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = PlacesPortal(route, parentComponent)
}

internal class PlacesPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = PlacesComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class PlacesComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@KmpComponentCreate
internal expect fun PlacesComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PlacesComponent
