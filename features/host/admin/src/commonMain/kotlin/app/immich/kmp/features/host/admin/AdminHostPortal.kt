package app.immich.kmp.features.host.admin

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.HostRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.TargetComponentAccessor

internal typealias Route = HostRoute.Admin
internal typealias View = AdminHostView
internal typealias Intent = AdminHostIntent
internal typealias Compositor = AdminHostCompositor
internal typealias Effects = AdminHostEffects
internal typealias ViewState = AdminHostViewState

object AdminHostPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = AdminHostPortal(route, parentComponent)
}

internal class AdminHostPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = AdminHostComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class AdminHostComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@TargetComponentAccessor
internal expect fun AdminHostComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): AdminHostComponent
