package com.eygraber.virtue.session

import com.eygraber.uri.Uri

public interface VirtueRoute {
  public val name: String get() = requireNotNull(this::class.simpleName) {
    "Don't make an anonymous subclass of VirtueRoute"
  }

  public fun toUri(): Uri
}
