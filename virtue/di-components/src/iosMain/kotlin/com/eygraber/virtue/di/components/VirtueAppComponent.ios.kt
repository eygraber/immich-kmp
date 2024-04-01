package com.eygraber.virtue.di.components

import com.eygraber.virtue.config.VirtueConfig
import me.tatarka.inject.annotations.TargetComponentAccessor

@TargetComponentAccessor
public expect fun VirtueAppComponent.Companion.createKmp(
  platformComponent: VirtuePlatformComponent,
  config: VirtueConfig,
): VirtueAppComponent
