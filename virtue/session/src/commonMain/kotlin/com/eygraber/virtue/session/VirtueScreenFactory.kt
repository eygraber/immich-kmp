package com.eygraber.virtue.session

public interface VirtueScreenFactory<Key> {
  public operator fun invoke(key: Key): GenericVirtueScreen<out Key>
}

public class VirtueComponentScreenFactory<Component, Key>(
  private val component: Component,
  private val factory: (Component, Key) -> GenericVirtueScreen<out Key>,
) : VirtueScreenFactory<Key> {
  override operator fun invoke(key: Key): GenericVirtueScreen<out Key> = factory(component, key)
}
