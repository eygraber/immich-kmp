package app.immich.kmp.features.main.favorites

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun FavoritesComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): FavoritesComponent = FavoritesComponent.Companion.create(sessionComponent, route)
