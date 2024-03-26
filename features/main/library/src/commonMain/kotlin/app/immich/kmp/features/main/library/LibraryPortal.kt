package app.immich.kmp.features.main.library

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.Library
internal typealias View = LibraryView
internal typealias Intent = LibraryIntent
internal typealias Compositor = LibraryCompositor
internal typealias Effects = LibraryEffects
internal typealias ViewState = LibraryViewState

object LibraryPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = LibraryPortal(route, parentComponent)
}

internal class LibraryPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = LibraryComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class LibraryComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun LibraryComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): LibraryComponent
