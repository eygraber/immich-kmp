package app.immich.kmp.core

import com.eygraber.vice.ViceCompositor
import com.eygraber.vice.ViceEffects
import com.eygraber.vice.ViceView
import com.eygraber.virtue.session.VirtuePortal
import com.eygraber.virtue.session.VirtuePortalComponent
import me.tatarka.inject.annotations.Provides

abstract class ImmichSessionPortal<K, V, I, C, E, S> : VirtuePortal<K, V, I, C, E, S, ImmichSessionComponent>()
  where V : ViceView<I, S>, C : ViceCompositor<I, S>, E : ViceEffects {
  abstract override val parentComponent: ImmichSessionComponent
  abstract override val component: ImmichSessionPortalComponent<K, V, I, C, E, S>
}

interface ImmichSessionPortalComponent<K, V, I, C, E, S> :
  VirtuePortalComponent<V, I, C, E, S, ImmichSessionComponent>
  where V : ViceView<I, S>, C : ViceCompositor<I, S>, E : ViceEffects {
  @get:Provides val route: K
}
