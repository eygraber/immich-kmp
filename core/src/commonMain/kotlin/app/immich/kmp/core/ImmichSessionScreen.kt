package app.immich.kmp.core

import com.eygraber.vice.ViceCompositor
import com.eygraber.vice.ViceEffects
import com.eygraber.vice.ViceView
import com.eygraber.virtue.session.VirtueScreen
import com.eygraber.virtue.session.VirtueScreenComponent

abstract class ImmichSessionScreen<K, V, I, C, E, S> : VirtueScreen<K, V, I, C, E, S, ImmichSessionComponent>()
  where V : ViceView<I, S>, C : ViceCompositor<I, S>, E : ViceEffects {
  abstract override val parentComponent: ImmichSessionComponent
  abstract override val component: ImmichSessionScreenComponent<V, I, C, E, S>
}

interface ImmichSessionScreenComponent<V, I, C, E, S> :
  VirtueScreenComponent<V, I, C, E, S, ImmichSessionComponent>
  where V : ViceView<I, S>, C : ViceCompositor<I, S>, E : ViceEffects
