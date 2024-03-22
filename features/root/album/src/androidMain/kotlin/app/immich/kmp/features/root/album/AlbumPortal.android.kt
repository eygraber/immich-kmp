package app.immich.kmp.features.root.album

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun AlbumComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): AlbumComponent = AlbumComponent.Companion.create(sessionComponent, route)
