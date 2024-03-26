package com.eygraber.virtue.di.components

import app.immich.kmp.ksp.generate.actual.GenerateActual
import com.eygraber.virtue.config.VirtueConfig

@GenerateActual
public expect fun VirtueAppComponent.Companion.createKmp(
  platformComponent: VirtuePlatformComponent,
  config: VirtueConfig,
): VirtueAppComponent
