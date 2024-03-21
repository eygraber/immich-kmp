package app.immich.kmp.features.root.four.oh.four

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class FourOhFourEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
