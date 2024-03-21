package app.immich.kmp.features.main.sharing

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun SharingComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): SharingComponent = SharingComponent.Companion.create(sessionComponent, route)
