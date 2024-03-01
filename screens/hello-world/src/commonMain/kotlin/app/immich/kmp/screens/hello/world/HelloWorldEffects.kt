package app.immich.kmp.screens.hello.world

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionScreenSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionScreenSingleton
@Inject
internal class HelloWorldEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
