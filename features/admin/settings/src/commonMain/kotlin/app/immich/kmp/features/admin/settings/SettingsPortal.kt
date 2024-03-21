package app.immich.kmp.features.admin.settings

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = AdminRoute.Settings
internal typealias View = SettingsView
internal typealias Intent = SettingsIntent
internal typealias Compositor = SettingsCompositor
internal typealias Effects = SettingsEffects
internal typealias ViewState = SettingsViewState

object SettingsPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = SettingsPortal(route, parentComponent)
}

internal class SettingsPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = SettingsComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class SettingsComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun SettingsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): SettingsComponent
