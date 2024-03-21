package app.immich.kmp.router

import com.eygraber.uri.Uri

sealed interface MainRoute : ImmichRoute {
  data object Albums : MainRoute {
    override fun toUri(): Uri = uri("/albums")
  }

  data object Archive : MainRoute {
    override fun toUri(): Uri = uri("/archive")
  }

  data object Explore : MainRoute {
    override fun toUri(): Uri = uri("/explore")
  }

  data object Favorites : MainRoute {
    override fun toUri(): Uri = uri("/favorites")
  }

  data object Library : MainRoute {
    override fun toUri(): Uri = uri("/library")
  }

  data object Map : MainRoute {
    override fun toUri(): Uri = uri("/map")
  }

  data object People : MainRoute {
    override fun toUri(): Uri = uri("/people")
  }

  data object Photos : MainRoute {
    override fun toUri(): Uri = uri("/photos")
  }

  data object Places : MainRoute {
    override fun toUri(): Uri = uri("/places")
  }

  data object Sharing : MainRoute {
    override fun toUri(): Uri = uri("/sharing")
  }

  data object Trash : MainRoute {
    override fun toUri(): Uri = uri("/trash")
  }

  data object UserSettings : MainRoute {
    override fun toUri(): Uri = uri("/user-settings")
  }

  companion object {
    fun fromUri(uri: Uri): MainRoute? = when(val path = uri.path) {
      null, "", "/" -> Photos
      else -> when {
        path.startsWith("/albums") -> Albums
        path.startsWith("/archive") -> Archive
        path.startsWith("/explore") -> Explore
        path.startsWith("/favorites") -> Favorites
        path.startsWith("/library") -> Library
        path.startsWith("/map") -> Map
        path.startsWith("/people") -> People
        path.startsWith("/photos") -> Photos
        path.startsWith("/places") -> Places
        path.startsWith("/sharing") -> Sharing
        path.startsWith("/trash") -> Trash
        path.startsWith("/user-settings") -> UserSettings
        else -> null
      }
    }
  }
}
