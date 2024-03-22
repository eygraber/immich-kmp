package app.immich.kmp.features.root.four.oh.four

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = RootRoute.FourOhFour
internal typealias View = FourOhFourView
internal typealias Intent = FourOhFourIntent
internal typealias Compositor = FourOhFourCompositor
internal typealias Effects = FourOhFourEffects
internal typealias ViewState = FourOhFourViewState

object FourOhFourPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = FourOhFourPortal(route, parentComponent)
}

internal class FourOhFourPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = FourOhFourComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class FourOhFourComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun FourOhFourComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): FourOhFourComponent
