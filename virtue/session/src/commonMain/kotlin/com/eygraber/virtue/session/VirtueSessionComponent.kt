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
public abstract class VirtueSessionComponent<Component, Route, Router>(
  portalFactory: (Component, Route) -> GenericVirtuePortal<out Route>,
) : BaseVirtueSessionComponent
  where Component : VirtueSessionComponent<Component, Route, Router>,
        Route : VirtueRoute,
        Router : VirtuePortalRouter<Route> {
  @Provides public fun provideVirtueSessionComponent(): VirtueSessionComponent<Component, Route, Router> = this

  @get:Provides public val portalManager: ComposePortalManager<Route> by lazy {
    createPortalManager()
  }

  @Suppress("UNCHECKED_CAST")
  @get:Provides public val portalFactory: VirtuePortalFactory<Route> by lazy {
    VirtueComponentPortalFactory(this as Component, portalFactory)
  }

  @Provides public abstract fun Router.bind(): VirtueRouter

  protected open fun createPortalManager(): ComposePortalManager<Route> = ComposePortalManager()
}

public typealias GenericVirtueSessionComponent = VirtueSessionComponent<*, *, *>
