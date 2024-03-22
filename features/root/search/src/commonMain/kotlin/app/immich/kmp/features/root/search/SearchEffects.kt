package app.immich.kmp.features.root.search

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class SearchEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
