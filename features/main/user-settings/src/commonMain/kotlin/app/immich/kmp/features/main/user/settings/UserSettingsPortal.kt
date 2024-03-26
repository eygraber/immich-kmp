package app.immich.kmp.features.main.user.settings

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.UserSettings
internal typealias View = UserSettingsView
internal typealias Intent = UserSettingsIntent
internal typealias Compositor = UserSettingsCompositor
internal typealias Effects = UserSettingsEffects
internal typealias ViewState = UserSettingsViewState

object UserSettingsPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = UserSettingsPortal(route, parentComponent)
}

internal class UserSettingsPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = UserSettingsComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class UserSettingsComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun UserSettingsComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): UserSettingsComponent
