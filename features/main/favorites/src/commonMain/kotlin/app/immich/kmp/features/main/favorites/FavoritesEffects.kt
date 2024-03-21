package app.immich.kmp.features.main.favorites

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class FavoritesEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
