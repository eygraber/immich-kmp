package app.immich.kmp.features.admin.external.libraries

import androidx.compose.runtime.Composable
import com.eygraber.vice.ViceCompositor
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class ExternalLibrariesCompositor : ViceCompositor<Intent, ViewState>() {
  @Composable
  override fun composite() = ViewState
}
