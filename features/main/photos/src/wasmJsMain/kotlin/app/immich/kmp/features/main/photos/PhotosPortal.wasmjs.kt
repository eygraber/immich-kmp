package app.immich.kmp.features.main.photos

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun PhotosComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PhotosComponent = PhotosComponent.Companion.create(sessionComponent, route)
