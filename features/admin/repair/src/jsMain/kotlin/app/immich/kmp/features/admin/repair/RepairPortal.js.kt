package app.immich.kmp.features.admin.repair

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun RepairComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): RepairComponent = RepairComponent.Companion.create(sessionComponent, route)
