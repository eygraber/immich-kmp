package app.immich.kmp.screens.hello.world

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.immich.kmp.icons.ImmichLogoVector
import com.eygraber.vice.ViceView
import com.eygraber.virtue.di.scopes.SessionScreenSingleton
import me.tatarka.inject.annotations.Inject

@SessionScreenSingleton
@Inject
internal class HelloWorldView : ViceView<Intent, ViewState> {
  @Composable
  override fun Render(state: ViewState, onIntent: (Intent) -> Unit) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(ImmichLogoVector, contentDescription = "Immich Logo")
      Text(text = "Hello, world!")
    }
  }
}
