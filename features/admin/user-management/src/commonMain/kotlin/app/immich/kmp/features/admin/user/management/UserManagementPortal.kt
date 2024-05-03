package app.immich.kmp.features.admin.user.management

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate

internal typealias Route = AdminRoute.UserManagement
internal typealias View = UserManagementView
internal typealias Intent = UserManagementIntent
internal typealias Compositor = UserManagementCompositor
internal typealias Effects = UserManagementEffects
internal typealias ViewState = UserManagementViewState

object UserManagementPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = UserManagementPortal(route, parentComponent)
}

internal class UserManagementPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = UserManagementComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class UserManagementComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@KmpComponentCreate
internal expect fun UserManagementComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): UserManagementComponent
