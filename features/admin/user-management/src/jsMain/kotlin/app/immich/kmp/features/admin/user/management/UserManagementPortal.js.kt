package app.immich.kmp.features.admin.user.management

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun UserManagementComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): UserManagementComponent = UserManagementComponent.Companion.create(sessionComponent, route)
