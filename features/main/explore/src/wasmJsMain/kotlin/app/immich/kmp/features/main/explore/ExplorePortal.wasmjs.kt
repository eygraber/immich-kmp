package app.immich.kmp.features.main.explore

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun ExploreComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ExploreComponent = ExploreComponent.Companion.create(sessionComponent, route)
