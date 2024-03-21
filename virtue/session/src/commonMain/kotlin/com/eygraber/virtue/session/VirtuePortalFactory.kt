package com.eygraber.virtue.session

public interface VirtuePortalFactory<R : VirtueRoute> {
  public operator fun invoke(route: R): GenericVirtuePortal<out R>
}

public class VirtueComponentPortalFactory<Component, R : VirtueRoute>(
  private val component: Component,
  private val factory: (Component, R) -> GenericVirtuePortal<out R>,
) : VirtuePortalFactory<R> {
  override operator fun invoke(route: R): GenericVirtuePortal<out R> = factory(component, route)
}
