package app.immich.kmp.features.main.archive

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.Archive
internal typealias View = ArchiveView
internal typealias Intent = ArchiveIntent
internal typealias Compositor = ArchiveCompositor
internal typealias Effects = ArchiveEffects
internal typealias ViewState = ArchiveViewState

object ArchivePortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = ArchivePortal(route, parentComponent)
}

internal class ArchivePortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = ArchiveComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class ArchiveComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun ArchiveComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ArchiveComponent
