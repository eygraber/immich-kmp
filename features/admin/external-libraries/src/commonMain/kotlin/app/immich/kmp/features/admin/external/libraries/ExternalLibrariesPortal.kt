package app.immich.kmp.features.admin.external.libraries

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.AdminRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = AdminRoute.ExternalLibraries
internal typealias View = ExternalLibrariesView
internal typealias Intent = ExternalLibrariesIntent
internal typealias Compositor = ExternalLibrariesCompositor
internal typealias Effects = ExternalLibrariesEffects
internal typealias ViewState = ExternalLibrariesViewState

object ExternalLibrariesPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = ExternalLibrariesPortal(route, parentComponent)
}

internal class ExternalLibrariesPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = ExternalLibrariesComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class ExternalLibrariesComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun ExternalLibrariesComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ExternalLibrariesComponent
