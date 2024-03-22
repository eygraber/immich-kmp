package app.immich.kmp.router

import com.eygraber.uri.Uri

sealed interface HostRoute : ImmichRoute {
  data object Admin : HostRoute {
    override fun toUri(): Uri = ImmichRoute.RootUri
  }

  data object Main : HostRoute {
    override fun toUri(): Uri = ImmichRoute.RootUri
  }
}
