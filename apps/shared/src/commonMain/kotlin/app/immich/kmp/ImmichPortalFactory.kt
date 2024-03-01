package app.immich.kmp

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.router.ImmichNav
import app.immich.kmp.screens.hello.world.HelloWorldScreenFactory
import com.eygraber.virtue.session.GenericVirtueScreen

fun immichSessionScreenFactory(): (ImmichSessionComponent, ImmichNav) -> GenericVirtueScreen<out ImmichNav> =
  { component, key ->
    when(key) {
      is ImmichNav.HelloWorld -> HelloWorldScreenFactory(key, component)
    }
  }
