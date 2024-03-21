package app.immich.kmp.features.admin.server.stats

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = AdminRoute.ServerStats
internal typealias View = ServerStatsView
internal typealias Intent = ServerStatsIntent
internal typealias Compositor = ServerStatsCompositor
internal typealias Effects = ServerStatsEffects
internal typealias ViewState = ServerStatsViewState

object ServerStatsPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = ServerStatsPortal(route, parentComponent)
}

internal class ServerStatsPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = ServerStatsComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class ServerStatsComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun ServerStatsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ServerStatsComponent
