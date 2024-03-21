package app.immich.kmp.features.main.archive

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun ArchiveComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ArchiveComponent = ArchiveComponent.Companion.create(sessionComponent, route)
