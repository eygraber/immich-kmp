package app.immich.kmp.features.admin.jobs

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

internal typealias Route = AdminRoute.Jobs
internal typealias View = JobsView
internal typealias Intent = JobsIntent
internal typealias Compositor = JobsCompositor
internal typealias Effects = JobsEffects
internal typealias ViewState = JobsViewState

object JobsPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = JobsPortal(route, parentComponent)
}

internal class JobsPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = JobsComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class JobsComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@KmpComponentCreate
internal expect fun JobsComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): JobsComponent
