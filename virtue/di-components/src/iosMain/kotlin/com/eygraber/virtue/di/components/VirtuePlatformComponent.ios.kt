package com.eygraber.virtue.di.components

import app.immich.kmp.ksp.generate.actual.GenerateActual
import me.tatarka.inject.annotations.Component

@Suppress("UtilityClassWithPublicConstructor")
@Component
public actual abstract class VirtuePlatformComponent {
  public actual companion object
}

@GenerateActual
public expect fun VirtuePlatformComponent.Companion.createKmp(): VirtuePlatformComponent
