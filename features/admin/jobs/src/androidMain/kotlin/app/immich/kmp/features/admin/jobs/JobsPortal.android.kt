package app.immich.kmp.features.admin.jobs

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun JobsComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): JobsComponent = JobsComponent.Companion.create(sessionComponent, route)
