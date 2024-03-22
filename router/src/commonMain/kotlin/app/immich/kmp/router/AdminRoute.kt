package app.immich.kmp.router

import com.eygraber.uri.Uri

sealed interface AdminRoute : ImmichRoute {
  data object ExternalLibraries : AdminRoute {
    override fun toUri(): Uri = uri("/admin/libraries")
  }

  data object Jobs : AdminRoute {
    override fun toUri(): Uri = uri("/admin/jobs")
  }

  data object Repair : AdminRoute {
    override fun toUri(): Uri = uri("/admin/repair")
  }

  data object ServerStats : AdminRoute {
    override fun toUri(): Uri = uri("/admin/server-stats")
  }

  data object Settings : AdminRoute {
    override fun toUri(): Uri = uri("/admin/settings")
  }

  data object UserManagement : AdminRoute {
    override fun toUri(): Uri = uri("/admin/user-management")
  }

  companion object {
    fun fromUri(uri: Uri): AdminRoute? = when(val path = uri.path) {
      null -> null
      else -> when {
        path.startsWith("/admin/jobs") -> Jobs
        path.startsWith("/admin/repair") -> Repair
        path.startsWith("/admin/server-stats") -> ServerStats
        path.startsWith("/admin/settings") -> Settings
        path.startsWith("/admin/user-management") -> UserManagement
        else -> null
      }
    }
  }
}
