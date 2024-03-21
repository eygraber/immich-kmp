package app.immich.kmp.features.main.user.settings

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun UserSettingsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): UserSettingsComponent = UserSettingsComponent.Companion.create(sessionComponent, route)
