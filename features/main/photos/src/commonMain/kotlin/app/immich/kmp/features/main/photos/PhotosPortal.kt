package app.immich.kmp.features.main.photos

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionPortal
import app.immich.kmp.core.ImmichSessionPortalComponent
import app.immich.kmp.router.MainRoute
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.Component

internal typealias Route = MainRoute.Photos
internal typealias View = PhotosView
internal typealias Intent = PhotosIntent
internal typealias Compositor = PhotosCompositor
internal typealias Effects = PhotosEffects
internal typealias ViewState = PhotosViewState

object PhotosPortalFactory {
  operator fun invoke(
    route: Route,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtuePortal<Route> = PhotosPortal(route, parentComponent)
}

internal class PhotosPortal(
  override val route: Route,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionPortal<Route, View, Intent, Compositor, Effects, ViewState>() {
  // https://github.com/evant/kotlin-inject/pull/362
  override val component = PhotosComponent.createA(
    sessionComponent = parentComponent,
    route = route,
  )
}

@SessionPortalSingleton
@Component
internal abstract class PhotosComponent(
  @Component override val parentComponent: ImmichSessionComponent,
  override val route: Route,
) : ImmichSessionPortalComponent<Route, View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun PhotosComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PhotosComponent
