package app.immich.kmp.screens.hello.world

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun HelloWorldComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
): HelloWorldComponent = HelloWorldComponent.Companion.create(sessionComponent)
