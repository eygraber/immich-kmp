package app.immich.kmp.features.main.people

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.People
internal typealias View = PeopleView
internal typealias Intent = PeopleIntent
internal typealias Compositor = PeopleCompositor
internal typealias Effects = PeopleEffects
internal typealias ViewState = PeopleViewState

object PeoplePortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = PeoplePortal(route, parentComponent)
}

internal class PeoplePortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = PeopleComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class PeopleComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun PeopleComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PeopleComponent
