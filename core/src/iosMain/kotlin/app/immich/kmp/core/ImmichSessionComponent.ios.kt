package app.immich.kmp.core

import app.immich.kmp.router.ImmichRoute
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.session.GenericVirtuePortal
import me.tatarka.inject.annotations.TargetComponentAccessor

@TargetComponentAccessor
expect fun ImmichSessionComponent.Companion.createKmp(
  appComponent: ImmichAppComponent,
  virtuePlatformSessionComponent: VirtuePlatformSessionComponent,
  portalFactory: (ImmichSessionComponent, ImmichRoute) -> GenericVirtuePortal<out ImmichRoute>,
): ImmichSessionComponent
