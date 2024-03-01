package app.immich.kmp.screens.hello.world

import androidx.compose.runtime.Composable
import com.eygraber.vice.ViceCompositor
import com.eygraber.virtue.di.scopes.SessionScreenSingleton
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@SessionScreenSingleton
@Inject
internal class HelloWorldCompositor : ViceCompositor<Intent, ViewState>() {
  @Composable
  override fun composite(intents: Flow<Intent>): ViewState = ViewState
}
