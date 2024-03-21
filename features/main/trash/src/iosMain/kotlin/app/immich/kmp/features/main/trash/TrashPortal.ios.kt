package app.immich.kmp.features.main.trash

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun TrashComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): TrashComponent = TrashComponent.Companion.create(sessionComponent, route)
