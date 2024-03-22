package app.immich.kmp.features.admin.user.management

import com.eygraber.vice.ViceEffects
import com.eygraber.virtue.di.scopes.SessionPortalSingleton
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject

@SessionPortalSingleton
@Inject
internal class UserManagementEffects : ViceEffects() {
  override fun CoroutineScope.onInitialized() {}
}
