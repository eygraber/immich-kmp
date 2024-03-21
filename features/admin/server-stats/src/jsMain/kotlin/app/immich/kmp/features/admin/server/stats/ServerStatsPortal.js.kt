package app.immich.kmp.features.admin.server.stats

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun ServerStatsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): ServerStatsComponent = ServerStatsComponent.Companion.create(sessionComponent, route)
