package app.immich.kmp.features.root.memory

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.ksp.generate.actual.GenerateActual
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = RootRoute.Memory
internal typealias View = MemoryView
internal typealias Intent = MemoryIntent
internal typealias Compositor = MemoryCompositor
internal typealias Effects = MemoryEffects
internal typealias ViewState = MemoryViewState

object MemoryPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = MemoryPortal(route, parentComponent)
}

internal class MemoryPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = MemoryComponent.createKmp(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class MemoryComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

@GenerateActual
internal expect fun MemoryComponent.Companion.createKmp(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): MemoryComponent
