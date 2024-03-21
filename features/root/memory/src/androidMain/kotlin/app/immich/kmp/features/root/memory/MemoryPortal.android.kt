package app.immich.kmp.features.root.memory

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun MemoryComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): MemoryComponent = MemoryComponent.Companion.create(sessionComponent, route)
