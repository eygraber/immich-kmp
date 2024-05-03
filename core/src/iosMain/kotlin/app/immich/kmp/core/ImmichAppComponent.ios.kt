package app.immich.kmp.core

import com.eygraber.virtue.di.components.VirtueAppComponent
import me.tatarka.inject.annotations.KmpComponentCreate

@KmpComponentCreate
expect fun ImmichAppComponent.Companion.createKmp(
  virtueAppComponent: VirtueAppComponent,
): ImmichAppComponent
