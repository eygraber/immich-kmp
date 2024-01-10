package app.immich.kmp.di.platform.components

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import app.immich.kmp.android.AppContext
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AndroidComponent(
  @get:Provides val context: AppContext,
) {
  @Provides fun contentResolver(): ContentResolver = context.contentResolver
  @Provides fun resources(): Resources = context.resources
  @Provides fun sharedPreferences(name: String): SharedPreferences =
    context.getSharedPreferences(
      name,
      Context.MODE_PRIVATE,
    )

  companion object
}
