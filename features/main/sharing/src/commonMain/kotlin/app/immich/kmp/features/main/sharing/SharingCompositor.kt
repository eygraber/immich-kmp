package app.immich.kmp.features.main.sharing

import androidx.compose.runtime.Composable
import com.eygraber.vice.ViceCompositor
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class SharingCompositor : ViceCompositor<Intent, ViewState>() {
  @Composable
  override fun composite() = ViewState
}
