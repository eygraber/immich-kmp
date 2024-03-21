package app.immich.kmp.features.root.album

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.immich.kmp.theme.ImmichPreviewTheme
import com.eygraber.vice.ViceView
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class AlbumView : ViceView<Intent, ViewState> {
  @Composable
  override fun Render(state: ViewState, onIntent: (Intent) -> Unit) {
    Album(state = state, onIntent = onIntent)
  }
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun Album(state: ViewState, onIntent: (Intent) -> Unit) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Text("Album")
  }
}

@Preview
@Composable
private fun AlbumPreview() {
  ImmichPreviewTheme {
    Album(
      state = ViewState,
      onIntent = {},
    )
  }
}
