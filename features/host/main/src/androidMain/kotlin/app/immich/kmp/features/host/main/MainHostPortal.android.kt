package app.immich.kmp.features.host.main

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun MainHostComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): MainHostComponent = MainHostComponent.Companion.create(sessionComponent, route)
