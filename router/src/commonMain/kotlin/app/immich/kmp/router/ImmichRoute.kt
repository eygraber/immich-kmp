package app.immich.kmp.router

import com.eygraber.uri.Uri
import com.eygraber.virtue.session.VirtueRoute

sealed interface ImmichRoute : VirtueRoute {
  companion object {
    val RootUri = Uri.parse("immich://")

    fun fromUri(
      @Suppress("UNUSED_PARAMETER") uri: Uri,
      decustomizedUri: Uri,
    ): ImmichRoute? = AdminRoute.fromUri(decustomizedUri)
      ?: MainRoute.fromUri(decustomizedUri)
      ?: RootRoute.fromUri(decustomizedUri)
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
