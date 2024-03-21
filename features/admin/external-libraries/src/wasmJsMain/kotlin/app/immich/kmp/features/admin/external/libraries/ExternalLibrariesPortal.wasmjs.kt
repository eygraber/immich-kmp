package app.immich.kmp.features.admin.external.libraries

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun ExternalLibrariesComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ExternalLibrariesComponent = ExternalLibrariesComponent.Companion.create(sessionComponent, route)
