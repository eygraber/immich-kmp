package app.immich.kmp.features.root.photo

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun PhotoComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PhotoComponent = PhotoComponent.Companion.create(sessionComponent, route)
