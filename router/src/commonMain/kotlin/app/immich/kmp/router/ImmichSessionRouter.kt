package app.immich.kmp.router

import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.history.History
import com.eygraber.virtue.session.VirtueScreenFactory
import com.eygraber.virtue.session.VirtueSessionScreenRouter
import me.tatarka.inject.annotations.Inject

@SessionSingleton
@Inject
class ImmichSessionRouter(
  private val screenFactory: VirtueScreenFactory<ImmichNav>,
  portalManager: ComposePortalManager<ImmichNav>,
) : VirtueSessionScreenRouter<ImmichNav>(
  portalManager,
) {
  override fun mapKeyToScreen(key: ImmichNav) = screenFactory(key)

  override fun mapHistoryItemToKey(item: History.Item): ImmichNav = ImmichNav.HelloWorld
}
