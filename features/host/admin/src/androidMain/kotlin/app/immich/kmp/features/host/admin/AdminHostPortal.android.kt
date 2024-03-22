package app.immich.kmp.features.host.admin

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun AdminHostComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): AdminHostComponent = AdminHostComponent.Companion.create(sessionComponent, route)
