package app.immich.kmp

import app.immich.kmp.core.ImmichSessionComponent
import app.immich.kmp.features.admin.external.libraries.ExternalLibrariesPortalFactory
import app.immich.kmp.features.admin.jobs.JobsPortalFactory
import app.immich.kmp.features.admin.repair.RepairPortalFactory
import app.immich.kmp.features.admin.server.stats.ServerStatsPortalFactory
import app.immich.kmp.features.admin.settings.SettingsPortalFactory
import app.immich.kmp.features.admin.user.management.UserManagementPortalFactory
import app.immich.kmp.features.host.admin.AdminHostPortalFactory
import app.immich.kmp.features.host.main.MainHostPortalFactory
import app.immich.kmp.features.main.albums.AlbumsPortalFactory
import app.immich.kmp.features.main.archive.ArchivePortalFactory
import app.immich.kmp.features.main.explore.ExplorePortalFactory
import app.immich.kmp.features.main.favorites.FavoritesPortalFactory
import app.immich.kmp.features.main.library.LibraryPortalFactory
import app.immich.kmp.features.main.map.MapPortalFactory
import app.immich.kmp.features.main.people.PeoplePortalFactory
import app.immich.kmp.features.main.photos.PhotosPortalFactory
import app.immich.kmp.features.main.places.PlacesPortalFactory
import app.immich.kmp.features.main.sharing.SharingPortalFactory
import app.immich.kmp.features.main.trash.TrashPortalFactory
import app.immich.kmp.features.main.user.settings.UserSettingsPortalFactory
import app.immich.kmp.features.root.album.AlbumPortalFactory
import app.immich.kmp.features.root.four.oh.four.FourOhFourPortalFactory
import app.immich.kmp.features.root.login.LoginPortalFactory
import app.immich.kmp.features.root.memory.MemoryPortalFactory
import app.immich.kmp.features.root.photo.PhotoPortalFactory
import app.immich.kmp.features.root.search.SearchPortalFactory
import app.immich.kmp.router.AdminRoute
import app.immich.kmp.router.HostRoute
import app.immich.kmp.router.ImmichRoute
import app.immich.kmp.router.MainRoute
import app.immich.kmp.router.RootRoute
import com.eygraber.virtue.session.GenericVirtuePortal

fun immichSessionPortalFactory(): (ImmichSessionComponent, ImmichRoute) -> GenericVirtuePortal<out ImmichRoute> =
  { component, route ->
    when(route) {
      is AdminRoute.ExternalLibraries -> ExternalLibrariesPortalFactory(route, component)
      is AdminRoute.Jobs -> JobsPortalFactory(route, component)
      is AdminRoute.Repair -> RepairPortalFactory(route, component)
      is AdminRoute.ServerStats -> ServerStatsPortalFactory(route, component)
      is AdminRoute.Settings -> SettingsPortalFactory(route, component)
      is AdminRoute.UserManagement -> UserManagementPortalFactory(route, component)

      is HostRoute.Admin -> AdminHostPortalFactory(route, component)
      is HostRoute.Main -> MainHostPortalFactory(route, component)

      is MainRoute.Albums -> AlbumsPortalFactory(route, component)
      is MainRoute.Archive -> ArchivePortalFactory(route, component)
      is MainRoute.Explore -> ExplorePortalFactory(route, component)
      is MainRoute.Favorites -> FavoritesPortalFactory(route, component)
      is MainRoute.Library -> LibraryPortalFactory(route, component)
      is MainRoute.Map -> MapPortalFactory(route, component)
      is MainRoute.People -> PeoplePortalFactory(route, component)
      is MainRoute.Photos -> PhotosPortalFactory(route, component)
      is MainRoute.Places -> PlacesPortalFactory(route, component)
      is MainRoute.Sharing -> SharingPortalFactory(route, component)
      is MainRoute.Trash -> TrashPortalFactory(route, component)
      is MainRoute.UserSettings -> UserSettingsPortalFactory(route, component)

      is RootRoute.Album -> AlbumPortalFactory(route, component)
      is RootRoute.FourOhFour -> FourOhFourPortalFactory(route, component)
      is RootRoute.Login -> LoginPortalFactory(route, component)
      is RootRoute.Memory -> MemoryPortalFactory(route, component)
      is RootRoute.Photo -> PhotoPortalFactory(route, component)
      is RootRoute.Search -> SearchPortalFactory(route, component)
    }
  }
