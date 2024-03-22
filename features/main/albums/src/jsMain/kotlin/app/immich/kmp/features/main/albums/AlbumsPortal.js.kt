package app.immich.kmp.features.main.albums

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun AlbumsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): AlbumsComponent = AlbumsComponent.Companion.create(sessionComponent, route)
