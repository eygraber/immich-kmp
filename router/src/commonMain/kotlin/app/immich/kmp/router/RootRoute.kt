package app.immich.kmp.router

import com.eygraber.uri.Uri

sealed interface RootRoute : ImmichRoute {
  data class Album(
    val id: String,
  ) : RootRoute {
    override fun toUri(): Uri = uri("/albums/$id")

    companion object {
      fun fromUri(uri: Uri) = Album(
        id = requireNotNull(uri.lastPathSegment) {
          "RootRoute.Album requires an id as the last segment in the path"
        },
      )
    }
  }

  data class FourOhFour(val uri: Uri) : RootRoute {
    override fun toUri(): Uri = uri
  }

  data object Login : RootRoute {
    override fun toUri(): Uri = uri("/auth/login")
  }

  data class Memory(
    val index: Int,
    val assetIndex: Int,
  ) : RootRoute {
    override fun toUri(): Uri = uri(
      path = "/memory",
      queryParams = mapOf(
        "memoryIndex" to index.toString(),
        "assetIndex" to assetIndex.toString(),
      ),
    )

    companion object {
      fun fromUri(uri: Uri) = Memory(
        index = requireNotNull(uri.getQueryParameter("memoryIndex")?.toIntOrNull()) {
          "RootRoute.Memory requires an integer memoryIndex"
        },
        assetIndex = uri.getQueryParameter("assetIndex")?.toIntOrNull() ?: 0,
      )
    }
  }

  data class Photo(
    val id: String,
  ) : RootRoute {
    override fun toUri(): Uri = uri("/photo/$id")

    companion object {
      fun fromUri(uri: Uri) = Photo(
        id = requireNotNull(uri.lastPathSegment) {
          "RootRoute.Photo requires an id as the last segment in the path"
        },
      )
    }
  }

  data class Search(
    val query: String?,
  ) : RootRoute {
    override fun toUri(): Uri = uri(
      path = "/search",
      queryParams = query?.let { mapOf("query" to query) }.orEmpty(),
    )

    companion object {
      fun fromUri(uri: Uri) = Search(
        query = uri.getQueryParameter("query"),
      )
    }
  }

  companion object {
    fun fromUri(uri: Uri): RootRoute? = when(val path = uri.path) {
      null -> null
      else -> when {
        path.startsWith("/auth/login") -> Login
        path.startsWith("/album/") && uri.pathSegments.size == 2 -> Album.fromUri(uri)
        path.startsWith("/photo/") && uri.pathSegments.size == 2 -> Photo.fromUri(uri)
        path.startsWith("/memory") -> Memory.fromUri(uri)
        path.startsWith("/search") -> Search.fromUri(uri)
        else -> null
      }
    }
  }
}
