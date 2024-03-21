package app.immich.kmp.features.main.map

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun MapComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): MapComponent = MapComponent.Companion.create(sessionComponent, route)
