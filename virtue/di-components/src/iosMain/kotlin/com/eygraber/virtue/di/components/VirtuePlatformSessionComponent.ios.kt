package com.eygraber.virtue.di.components

import app.immich.kmp.ksp.generate.actual.GenerateActual
import me.tatarka.inject.annotations.Component

@Suppress("UtilityClassWithPublicConstructor")
@Component
public actual abstract class VirtuePlatformSessionComponent {
  public actual companion object
}

@GenerateActual
public expect fun VirtuePlatformSessionComponent.Companion.createKmp(): VirtuePlatformSessionComponent
