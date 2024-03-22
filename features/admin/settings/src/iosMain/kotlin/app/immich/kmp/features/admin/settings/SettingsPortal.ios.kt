package app.immich.kmp.features.admin.settings

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun SettingsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): SettingsComponent = SettingsComponent.Companion.create(sessionComponent, route)
