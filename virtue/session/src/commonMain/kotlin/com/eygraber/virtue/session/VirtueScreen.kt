package com.eygraber.virtue.session

import androidx.compose.runtime.Composable
import com.eygraber.vice.ViceCompositor
import com.eygraber.vice.ViceEffects
import com.eygraber.vice.ViceView
import com.eygraber.vice.portal.VicePortal
import com.eygraber.virtue.back.press.dispatch.BackHandler

public abstract class VirtueScreen<K, V, I, C, E, S, out ParentComponent> : VicePortal<K, V, I, C, E, S>()
  where V : ViceView<I, S>,
        C : ViceCompositor<I, S>,
        E : ViceEffects {
  @Composable
  final override fun OnBackPressedHandler(enabled: Boolean, onBackPressed: () -> Unit) {
    BackHandler(enabled, onBackPressed)
  }

  protected abstract val parentComponent: ParentComponent
  protected abstract val component: VirtueScreenComponent<V, I, C, E, S, ParentComponent>

  final override val view: V by lazy { component.view }
  final override val compositor: C by lazy { component.compositor }
  final override val effects: E by lazy { component.effects }
}

public interface VirtueScreenComponent<V, I, C, E, S, out ParentComponent>
  where V : ViceView<I, S>, C : ViceCompositor<I, S>, E : ViceEffects {
  public val parentComponent: ParentComponent

  public val view: V
  public val compositor: C
  public val effects: E
}
