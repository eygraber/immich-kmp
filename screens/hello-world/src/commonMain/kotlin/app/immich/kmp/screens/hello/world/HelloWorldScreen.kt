package app.immich.kmp.screens.hello.world

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.core.ImmichSessionScreen
import app.immich.kmp.core.ImmichSessionScreenComponent
import app.immich.kmp.router.ImmichNav
import com.eygraber.virtue.di.scopes.SessionScreenSingleton
import com.eygraber.virtue.session.GenericVirtueScreen
import me.tatarka.inject.annotations.Component

internal typealias Nav = ImmichNav.HelloWorld
internal typealias View = HelloWorldView
internal typealias Intent = HelloWorldIntent
internal typealias Compositor = HelloWorldCompositor
internal typealias Effects = HelloWorldEffects
internal typealias ViewState = HelloWorldViewState

object HelloWorldScreenFactory {
  operator fun invoke(
    key: Nav,
    parentComponent: ImmichSessionComponent,
  ): GenericVirtueScreen<Nav> = HelloWorldScreen(key, parentComponent)
}

internal class HelloWorldScreen(
  override val key: Nav,
  override val parentComponent: ImmichSessionComponent,
) : ImmichSessionScreen<Nav, View, Intent, Compositor, Effects, ViewState>() {
  // TODO: https://github.com/evant/kotlin-inject/pull/362
  override val component = HelloWorldComponent.createA(
    sessionComponent = parentComponent,
  )
}

@SessionScreenSingleton
@Component
internal abstract class HelloWorldComponent(
  @Component override val parentComponent: ImmichSessionComponent,
) : ImmichSessionScreenComponent<View, Intent, Compositor, Effects, ViewState> {
  companion object
}

internal expect fun HelloWorldComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
): HelloWorldComponent
