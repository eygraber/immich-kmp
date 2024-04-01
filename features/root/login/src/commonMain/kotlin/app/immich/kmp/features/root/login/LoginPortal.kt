package app.immich.kmp.features.root.login

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.TargetComponentAccessor

internal typealias Route = RootRoute.Login
internal typealias View = LoginView
internal typealias Intent = LoginIntent
internal typealias Compositor = LoginCompositor
internal typealias Effects = LoginEffects
internal typealias ViewState = LoginViewState

object LoginPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = LoginPortal(route, parentComponent)
}

internal class LoginPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = LoginComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class LoginComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@TargetComponentAccessor
internal expect fun LoginComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): LoginComponent
