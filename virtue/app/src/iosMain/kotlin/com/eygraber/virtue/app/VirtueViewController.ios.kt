package com.eygraber.virtue.app

import androidx.compose.ui.window.ComposeUIViewController
import com.eygraber.uri.Uri
import com.eygraber.virtue.back.press.dispatch.WithBackPressDispatching
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.components.createKmp
import com.eygraber.virtue.session.BaseVirtueSessionComponent
import com.eygraber.virtue.session.GenericVirtueSessionComponent
import platform.UIKit.UIViewController

public fun <A : AppComponent, S : GenericVirtueSessionComponent> virtueViewController(
  model: VirtueApplicationModel<A>,
  sessionComponentFactory: (A, VirtuePlatformSessionComponent) -> S,
  defaultUri: Uri,
): UIViewController {
  val virtuePlatformSessionComponent = VirtuePlatformSessionComponent.createKmp()

  val sessionComponent = sessionComponentFactory(
    model.appComponent,
    virtuePlatformSessionComponent,
  ) as BaseVirtueSessionComponent

  return ComposeUIViewController {
    WithBackPressDispatching(
      onBackPressedDispatcher = sessionComponent.onBackPressedDispatcher,
    ) {
      sessionComponent.session.SessionUi(
        darkColorScheme = model.darkColorScheme,
        lightColorScheme = model.lightColorScheme,
        defaultUri = defaultUri,
      )
    }
  }
}
