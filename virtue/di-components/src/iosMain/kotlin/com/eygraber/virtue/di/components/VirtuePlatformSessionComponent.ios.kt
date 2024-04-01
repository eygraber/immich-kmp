package com.eygraber.virtue.di.components

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.TargetComponentAccessor

@Suppress("UtilityClassWithPublicConstructor")
@Component
public actual abstract class VirtuePlatformSessionComponent {
  public actual companion object
}

@TargetComponentAccessor
public expect fun VirtuePlatformSessionComponent.Companion.createKmp(): VirtuePlatformSessionComponent
