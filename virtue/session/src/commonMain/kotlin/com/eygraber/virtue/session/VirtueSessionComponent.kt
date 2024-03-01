package com.eygraber.virtue.session

import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.virtue.back.press.dispatch.OnBackPressDispatcherProvider
import com.eygraber.virtue.di.components.AppComponent
import com.eygraber.virtue.di.components.VirtuePlatformSessionComponent
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.history.HistoryProvider
import com.eygraber.virtue.session.state.VirtueSessionStateManager
import me.tatarka.inject.annotations.Provides

public interface BaseVirtueSessionComponent : HistoryProvider, OnBackPressDispatcherProvider {
  public val appComponent: AppComponent

  public val virtuePlatformSessionComponent: VirtuePlatformSessionComponent

  public val session: VirtueSession
  public val sessionManager: VirtueSessionManager
  public val stateManager: VirtueSessionStateManager
}

@SessionSingleton
public abstract class VirtueSessionComponent<Component, Key, Router>(
  screenFactory: (Component, Key) -> GenericVirtueScreen<out Key>,
) : BaseVirtueSessionComponent
  where Component : VirtueSessionComponent<Component, Key, Router>,
        Router : VirtueSessionScreenRouter<Key> {
  @Provides public fun provideVirtueSessionComponent(): VirtueSessionComponent<Component, Key, Router> = this

  @get:Provides public val portalManager: ComposePortalManager<Key> = ComposePortalManager()

  @Suppress("UNCHECKED_CAST")
  @get:Provides public val screenFactory: VirtueScreenFactory<Key> by lazy {
    VirtueComponentScreenFactory(this as Component, screenFactory)
  }

  @Provides public abstract fun Router.bind(): VirtueSessionRouter
}

public typealias GenericVirtueSessionComponent = VirtueSessionComponent<*, *, *>
