package app.immich.kmp.features.root.search

import app.immich.kmp.core.ImmichSessionComponent

internal actual fun SearchComponent.Companion.createA(
  sessionComponent: ImmichSessionComponent,
  route: Route,
): SearchComponent = SearchComponent.Companion.create(sessionComponent, route)
