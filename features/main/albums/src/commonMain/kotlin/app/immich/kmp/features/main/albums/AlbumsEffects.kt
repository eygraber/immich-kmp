package app.immich.kmp.features.main.albums

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class AlbumsEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
