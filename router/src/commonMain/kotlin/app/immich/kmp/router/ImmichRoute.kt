package app.immich.kmp.router

import com.eygraber.uri.Uri
import com.eygraber.virtue.session.VirtueRoute

sealed interface ImmichRoute : VirtueRoute {
  companion object {
    val RootUri = Uri.parse("immich://")

    fun fromUri(uri: Uri): ImmichRoute? =
      AdminRoute.fromUri(uri)
        ?: MainRoute.fromUri(uri)
        ?: RootRoute.fromUri(uri)
  }
}

internal fun uri(
  path: String,
  queryParams: Map<String, String> = emptyMap(),
): Uri = ImmichRoute.RootUri.buildUpon().apply {
  appendPath(path)
  for((k, v) in queryParams) {
    appendQueryParameter(k, v)
  }
}.build()
