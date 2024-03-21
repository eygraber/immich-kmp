package com.eygraber.virtue.session

import androidx.compose.runtime.Composable
import com.eygraber.portal.PortalManager
import com.eygraber.portal.compose.ComposePortal
import com.eygraber.portal.compose.ComposePortalManager
import com.eygraber.uri.Uri
import com.eygraber.virtue.di.scopes.SessionSingleton

@SessionSingleton
public abstract class VirtueRouter {
  internal abstract fun route(uri: Uri)

  @Composable public abstract fun Render()
}

public abstract class VirtuePortalRouter<R : VirtueRoute>(
  private val portalManager: ComposePortalManager<R>,
) : VirtueRouter() {
  final override fun route(uri: Uri) {
    val portal = mapUriToPortal(uri)
    portalManager.withTransaction {
      newPortalTransaction(portal)
    }
  }

  protected abstract fun mapUriToPortal(uri: Uri): GenericVirtuePortal<out R>

  protected open fun PortalManager.EntryBuilder<R>.newPortalTransaction(
    portal: ComposePortal<out R>,
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
