package com.eygraber.virtue.session

import androidx.compose.runtime.Composable
import com.eygraber.portal.PortalManager
import com.eygraber.portal.compose.ComposePortal
import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.history.History

@SessionSingleton
public interface VirtueSessionRouter {
  public fun onHistoryUpdated(currentItem: History.Item)

  @Composable public fun Render()
}

public abstract class VirtueSessionScreenRouter<Key>(
  private val portalManager: ComposePortalManager<Key>,
) : VirtueSessionRouter {
  final override fun onHistoryUpdated(currentItem: History.Item) {
    val key = mapHistoryItemToKey(currentItem)
    val portal = mapKeyToScreen(key)
    portalManager.withTransaction {
      newPortalTransaction(portal)
    }
  }

  protected abstract fun mapHistoryItemToKey(item: History.Item): Key

  protected abstract fun mapKeyToScreen(key: Key): GenericVirtueScreen<out Key>

  protected open fun PortalManager.EntryBuilder<Key>.newPortalTransaction(
    portal: ComposePortal<out Key>,
  ) {
    val currentPortal = portalEntries.firstOrNull()
    if(currentPortal != null) {
      remove(currentPortal.key)
    }

    add(portal)
  }

  @Composable
  final override fun Render() {
    portalManager.Render()
  }
}
