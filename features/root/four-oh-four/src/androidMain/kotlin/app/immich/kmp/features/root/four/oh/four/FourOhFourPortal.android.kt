package app.immich.kmp.features.root.four.oh.four

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun FourOhFourComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): FourOhFourComponent = FourOhFourComponent.Companion.create(sessionComponent, route)
