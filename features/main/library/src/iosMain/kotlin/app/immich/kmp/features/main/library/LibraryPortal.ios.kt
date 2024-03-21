package app.immich.kmp.features.main.library

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun LibraryComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): LibraryComponent = LibraryComponent.Companion.create(sessionComponent, route)
