package app.immich.kmp.features.root.login

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun LoginComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): LoginComponent = LoginComponent.Companion.create(sessionComponent, route)
