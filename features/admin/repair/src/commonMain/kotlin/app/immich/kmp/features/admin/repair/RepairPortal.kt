package app.immich.kmp.features.admin.repair

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.TargetComponentAccessor

internal typealias Route = AdminRoute.Repair
internal typealias View = RepairView
internal typealias Intent = RepairIntent
internal typealias Compositor = RepairCompositor
internal typealias Effects = RepairEffects
internal typealias ViewState = RepairViewState

object RepairPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = RepairPortal(route, parentComponent)
}

internal class RepairPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = RepairComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class RepairComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@TargetComponentAccessor
internal expect fun RepairComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): RepairComponent
