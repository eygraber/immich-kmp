package app.immich.kmp.features.main.places

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun PlacesComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PlacesComponent = PlacesComponent.Companion.create(sessionComponent, route)
