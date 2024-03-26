package app.immich.kmp.features.host.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.immich.kmp.icons.ImmichLogoVector
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.router.MainRoute
import app.immich.kmp.theme.ImmichPreviewTheme
import com.eygraber.vice.ViceView
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class MainHostView : ViceView<Intent, ViewState> {
  @Composable
  override fun Render(state: ViewState, onIntent: (Intent) -> Unit) {
    MainHost(state = state, onIntent = onIntent)
  }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Suppress("UNUSED_PARAMETER")
@Composable
private fun MainHost(state: ViewState, onIntent: (Intent) -> Unit) {
  val widthSizeClass = calculateWindowSizeClass().widthSizeClass
  val paddingForNavRailIfNeeded = when {
    widthSizeClass > WindowWidthSizeClass.Compact -> 80.dp
    else -> 0.dp
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Image(ImmichLogoVector, contentDescription = "Logo", modifier = Modifier.size(32.dp))
            Text("immich", modifier = Modifier.padding(start = 8.dp))
          }
        },
      )
    },
    bottomBar = {
      MainNavigationBar(
        selectedNavigationItem = MainRoute.Photos,
        onNavigationItemSelected = {},
        widthSizeClass = widthSizeClass,
      )
    },
  ) { contentPadding ->
    Row {
      Box {
        MainNavigationRail(
          widthSizeClass = widthSizeClass,
        )
      }

      Box(
        modifier = Modifier.fillMaxSize().padding(contentPadding).padding(start = paddingForNavRailIfNeeded),
        contentAlignment = Alignment.Center,
      ) {
        Text("Hello you are in Main")
      }
    }
  }
}

@Preview
@Composable
private fun MainHostPreview() {
  ImmichPreviewTheme {
    MainHost(
      state = ViewState,
      onIntent = {},
    )
  }
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun MainNavigationBar(
  selectedNavigationItem: ImmichRoute,
  onNavigationItemSelected: (ImmichRoute) -> Unit,
  widthSizeClass: WindowWidthSizeClass,
) {
  @Composable
  fun Animation(
    navigationBar: @Composable () -> Unit,
  ) {
    val isNavigationBarShowing = widthSizeClass < WindowWidthSizeClass.Medium

    AnimatedVisibility(
      visibleState = remember {
        MutableTransitionState(initialState = isNavigationBarShowing)
      }.apply {
        targetState = isNavigationBarShowing
      },
      enter = expandVertically(),
      exit = shrinkVertically(),
    ) {
      navigationBar()
    }
  }

  Animation {
    NavigationBar {
      // ShowcasePortalKey.residentPortals.forEach { item ->
      //   NavigationBarItem(
      //     selected = item == selectedNavigationItem,
      //     onClick = {
      //       onNavigationItemSelected(item)
      //     },
      //     icon = {
      //       if(item.icon != null) {
      //         Icon(item.icon, contentDescription = item.contentDescription)
      //       }
      //     },
      //     label = {
      //       Text(item.title)
      //     }
      //   )
      // }
    }
  }
}

@Composable
private fun MainNavigationRail(
  widthSizeClass: WindowWidthSizeClass,
) {
  @Composable
  fun Animation(
    navigationRail: @Composable () -> Unit,
  ) {
    val isNavigationRailShowing = widthSizeClass >= WindowWidthSizeClass.Medium

    AnimatedVisibility(
      visibleState = remember {
        MutableTransitionState(initialState = isNavigationRailShowing)
      }.apply {
        targetState = isNavigationRailShowing
      },
      enter = expandHorizontally(),
      exit = shrinkHorizontally(),
    ) {
      navigationRail()
    }
  }

  Animation {
    NavigationRail { }
  }
}
