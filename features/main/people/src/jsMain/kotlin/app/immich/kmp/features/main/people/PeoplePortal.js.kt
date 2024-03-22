package app.immich.kmp.features.main.people

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun PeopleComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): PeopleComponent = PeopleComponent.Companion.create(sessionComponent, route)
