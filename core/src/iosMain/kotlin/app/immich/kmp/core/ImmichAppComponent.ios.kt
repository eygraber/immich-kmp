package app.immich.kmp.core

import app.immich.kmp.ksp.generate.actual.GenerateActual
import com.eygraber.virtue.di.components.VirtueAppComponent

@GenerateActual
expect fun ImmichAppComponent.Companion.createKmp(
  virtueAppComponent: VirtueAppComponent,
): ImmichAppComponent
