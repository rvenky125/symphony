package io.github.zyrouge.symphony.services

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import androidx.core.content.edit
import io.github.zyrouge.symphony.Symphony
import io.github.zyrouge.symphony.services.groove.*
import io.github.zyrouge.symphony.services.radio.RadioQueue
import io.github.zyrouge.symphony.ui.theme.ThemeMode
import io.github.zyrouge.symphony.ui.view.HomePageBottomBarLabelVisibility
import io.github.zyrouge.symphony.ui.view.HomePages
import io.github.zyrouge.symphony.ui.view.NowPlayingControlsLayout
import io.github.zyrouge.symphony.ui.view.home.ForYou
import io.github.zyrouge.symphony.utils.Eventer

object SettingsKeys {
    const val identifier = "settings"
    const val themeMode = "theme_mode"
    const val language = "language"
    const val materialYou = "material_you"
    const val lastUsedSongsSortBy = "last_used_song_sort_by"
    const val lastUsedSongsSortReverse = "last_used_song_sort_reverse"
    const val lastUsedArtistsSortBy = "last_used_artists_sort_by"
    const val lastUsedArtistsSortReverse = "last_used_artists_sort_reverse"
    const val lastUsedAlbumArtistsSortBy = "last_used_album_artists_sort_by"
    const val lastUsedAlbumArtistsSortReverse = "last_used_album_artists_sort_reverse"
    const val lastUsedAlbumsSortBy = "last_used_albums_sort_by"
    const val lastUsedAlbumsSortReverse = "last_used_albums_sort_reverse"
    const val lastUsedGenresSortBy = "last_used_genres_sort_by"
    const val lastUsedGenresSortReverse = "last_used_genres_sort_reverse"
    const val lastUsedFolderSortBy = "last_used_folder_sort_by"
    const val lastUsedFolderSortReverse = "last_used_folder_sort_reverse"
    const val lastUsedFolderPath = "last_used_folder_path"
    const val lastUsedPlaylistsSortBy = "last_used_playlists_sort_by"
    const val lastUsedPlaylistsSortReverse = "last_used_playlists_sort_reverse"
    const val lastUsedPlaylistSongsSortBy = "last_used_playlist_songs_sort_by"
    const val lastUsedPlaylistSongsSortReverse = "last_used_playlist_songs_sort_reverse"
    const val lastUsedAlbumSongsSortBy = "last_used_album_songs_sort_by"
    const val lastUsedAlbumSongsSortReverse = "last_used_album_songs_sort_reverse"
    const val lastUsedTreePathSortBy = "last_used_tree_path_sort_by"
    const val lastUsedTreePathSortReverse = "last_used_tree_path_sort_reverse"
    const val lastDisabledTreePaths = "last_disabled_tree_paths"
    const val previousSongQueue = "previous_song_queue"
    const val homeLastTab = "home_last_page"
    const val songsFilterPattern = "songs_filter_pattern"
    const val checkForUpdates = "check_for_updates"
    const val fadePlayback = "fade_playback"
    const val requireAudioFocus = "require_audio_focus"
    const val ignoreAudioFocusLoss = "ignore_audio_focus_loss"
    const val playOnHeadphonesConnect = "play_on_headphones_connect"
    const val pauseOnHeadphonesDisconnect = "pause_on_headphones_disconnect"
    const val primaryColor = "primary_color"
    const val fadePlaybackDuration = "fade_playback_duration"
    const val homeTabs = "home_tabs"
    const val homePageBottomBarLabelVisibility = "home_page_bottom_bar_label_visibility"
    const val forYouContents = "for_you_contents"
    const val blacklistFolders = "blacklist_folders"
    const val whitelistFolders = "whitelist_folders"
    const val readIntroductoryMessage = "introductory_message"
    const val nowPlayingAdditionalInfo = "show_now_playing_additional_info"
    const val nowPlayingSeekControls = "enable_seek_controls"
    const val seekBackDuration = "seek_back_duration"
    const val seekForwardDuration = "seek_forward_duration"
    const val miniPlayerTrackControls = "mini_player_extended_controls"
    const val miniPlayerSeekControls = "mini_player_seek_controls"
    const val fontFamily = "font_family"
    const val nowPlayingControlsLayout = "now_playing_controls_layout"
}

data class SettingsData(
    val themeMode: ThemeMode,
    val language: String?,
    val useMaterialYou: Boolean,
    val songsFilterPattern: String?,
    val checkForUpdates: Boolean,
    val fadePlayback: Boolean,
    val requireAudioFocus: Boolean,
    val ignoreAudioFocusLoss: Boolean,
    val playOnHeadphonesConnect: Boolean,
    val pauseOnHeadphonesDisconnect: Boolean,
    val primaryColor: String?,
    val fadePlaybackDuration: Float,
    val homeTabs: Set<HomePages>,
    val homePageBottomBarLabelVisibility: HomePageBottomBarLabelVisibility,
    val forYouContents: Set<ForYou>,
    val blacklistFolders: Set<String>,
    val whitelistFolders: Set<String>,
    val nowPlayingAdditionalInfo: Boolean,
    val nowPlayingSeekControls: Boolean,
    val seekBackDuration: Int,
    val seekForwardDuration: Int,
    val miniPlayerTrackControls: Boolean,
    val miniPlayerSeekControls: Boolean,
    val fontFamily: String?,
    val nowPlayingControlsLayout: NowPlayingControlsLayout,
)

object SettingsDataDefaults {
    val themeMode = ThemeMode.SYSTEM
    const val useMaterialYou = true
    const val checkForUpdates = true
    const val fadePlayback = false
    const val requireAudioFocus = true
    const val ignoreAudioFocusLoss = false
    const val playOnHeadphonesConnect = false
    const val pauseOnHeadphonesDisconnect = true
    const val fadePlaybackDuration = 1f
    val homeTabs = setOf(
        HomePages.ForYou,
        HomePages.Songs,
        HomePages.Albums,
        HomePages.Artists
    )
    val homePageBottomBarLabelVisibility = HomePageBottomBarLabelVisibility.ALWAYS_VISIBLE
    val forYouContents = setOf(
        ForYou.Albums,
        ForYou.Artists
    )
    val blacklistFolders = setOf<String>(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).path,
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).path,
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).path,
    )
    val whitelistFolders = setOf<String>()
    const val readIntroductoryMessage = false
    const val showNowPlayingAdditionalInfo = true
    const val enableSeekControls = false
    const val seekBackDuration = 15
    const val seekForwardDuration = 30
    const val miniPlayerTrackControls = false
    const val miniPlayerSeekControls = false
    val nowPlayingControlsLayout = NowPlayingControlsLayout.Default
}

class SettingsManager(private val symphony: Symphony) {
    val onChange = Eventer<String>()

    fun getSettings() = SettingsData(
        themeMode = getThemeMode(),
        language = getLanguage(),
        useMaterialYou = getUseMaterialYou(),
        songsFilterPattern = getSongsFilterPattern(),
        checkForUpdates = getCheckForUpdates(),
        fadePlayback = getFadePlayback(),
        requireAudioFocus = getRequireAudioFocus(),
        ignoreAudioFocusLoss = getIgnoreAudioFocusLoss(),
        playOnHeadphonesConnect = getPlayOnHeadphonesConnect(),
        pauseOnHeadphonesDisconnect = getPauseOnHeadphonesDisconnect(),
        primaryColor = getPrimaryColor(),
        fadePlaybackDuration = getFadePlaybackDuration(),
        homeTabs = getHomeTabs(),
        homePageBottomBarLabelVisibility = getHomePageBottomBarLabelVisibility(),
        forYouContents = getForYouContents(),
        blacklistFolders = getBlacklistFolders(),
        whitelistFolders = getWhitelistFolders(),
        nowPlayingAdditionalInfo = getNowPlayingAdditionalInfo(),
        nowPlayingSeekControls = getNowPlayingSeekControls(),
        seekBackDuration = getSeekBackDuration(),
        seekForwardDuration = getSeekForwardDuration(),
        miniPlayerTrackControls = getMiniPlayerTrackControls(),
        miniPlayerSeekControls = getMiniPlayerSeekControls(),
        fontFamily = getFontFamily(),
        nowPlayingControlsLayout = getNowPlayingControlsLayout(),
    )

    fun getThemeMode() = getSharedPreferences().getString(SettingsKeys.themeMode, null)
        ?.let { ThemeMode.valueOf(it) }
        ?: SettingsDataDefaults.themeMode

    fun setThemeMode(themeMode: ThemeMode) {
        getSharedPreferences().edit {
            putString(SettingsKeys.themeMode, themeMode.name)
        }
        onChange.dispatch(SettingsKeys.themeMode)
    }

    fun getLanguage() = getSharedPreferences().getString(SettingsKeys.language, null)
    fun setLanguage(language: String) {
        getSharedPreferences().edit {
            putString(SettingsKeys.language, language)
        }
        onChange.dispatch(SettingsKeys.language)
    }

    fun getUseMaterialYou() = getSharedPreferences().getBoolean(
        SettingsKeys.materialYou,
        SettingsDataDefaults.useMaterialYou,
    )

    fun setUseMaterialYou(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.materialYou, value)
        }
        onChange.dispatch(SettingsKeys.materialYou)
    }

    fun getLastUsedSongsSortBy() =
        getSharedPreferences().getEnum<SongSortBy>(SettingsKeys.lastUsedSongsSortBy, null)

    fun setLastUsedSongsSortBy(sortBy: SongSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedSongsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedSongsSortBy)
    }

    fun getLastUsedSongsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedSongsSortReverse, false)

    fun setLastUsedSongsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedSongsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedSongsSortReverse)
    }

    fun getLastUsedArtistsSortBy() =
        getSharedPreferences().getEnum<ArtistSortBy>(SettingsKeys.lastUsedArtistsSortBy, null)

    fun setLastUsedArtistsSortBy(sortBy: ArtistSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedArtistsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedArtistsSortBy)
    }

    fun getLastUsedArtistsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedArtistsSortReverse, false)

    fun setLastUsedArtistsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedArtistsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedArtistsSortReverse)
    }

    fun getLastUsedAlbumArtistsSortBy() =
        getSharedPreferences().getEnum<AlbumArtistSortBy>(
            SettingsKeys.lastUsedAlbumArtistsSortBy,
            null
        )

    fun setLastUsedAlbumArtistsSortBy(sortBy: AlbumArtistSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedAlbumArtistsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumArtistsSortBy)
    }

    fun getLastUsedAlbumArtistsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedAlbumArtistsSortReverse, false)

    fun setLastUsedAlbumArtistsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedAlbumArtistsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumArtistsSortReverse)
    }

    fun getLastUsedAlbumsSortBy() =
        getSharedPreferences().getEnum<AlbumSortBy>(SettingsKeys.lastUsedAlbumsSortBy, null)

    fun setLastUsedAlbumsSortBy(sortBy: AlbumSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedAlbumsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumsSortBy)
    }

    fun getLastUsedAlbumsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedAlbumsSortReverse, false)

    fun setLastUsedAlbumsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedAlbumsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumsSortReverse)
    }

    fun getLastUsedGenresSortBy() =
        getSharedPreferences().getEnum<GenreSortBy>(SettingsKeys.lastUsedGenresSortBy, null)

    fun setLastUsedGenresSortBy(sortBy: GenreSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedGenresSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedGenresSortBy)
    }

    fun getLastUsedGenresSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedGenresSortReverse, false)

    fun setLastUsedGenresSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedGenresSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedGenresSortReverse)
    }

    fun getLastUsedFolderSortBy() =
        getSharedPreferences().getEnum<SongSortBy>(SettingsKeys.lastUsedFolderSortBy, null)

    fun setLastUsedFolderSortBy(sortBy: SongSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedFolderSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedFolderSortBy)
    }

    fun getLastUsedFolderSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedFolderSortReverse, false)

    fun setLastUsedFolderSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedFolderSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedFolderSortReverse)
    }

    fun getLastUsedFolderPath() =
        getSharedPreferences().getString(SettingsKeys.lastUsedFolderPath, null)?.split("/")

    fun setLastUsedFolderPath(path: List<String>) {
        getSharedPreferences().edit {
            putString(SettingsKeys.lastUsedFolderPath, path.joinToString("/"))
        }
        onChange.dispatch(SettingsKeys.lastUsedFolderPath)
    }

    fun getLastUsedPlaylistsSortBy() =
        getSharedPreferences().getEnum<PlaylistSortBy>(SettingsKeys.lastUsedPlaylistsSortBy, null)

    fun setLastUsedPlaylistsSortBy(sortBy: PlaylistSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedPlaylistsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedPlaylistsSortBy)
    }

    fun getLastUsedPlaylistsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedPlaylistsSortReverse, false)

    fun setLastUsedPlaylistsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedPlaylistsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedPlaylistsSortReverse)
    }

    fun getLastUsedPlaylistSongsSortBy() =
        getSharedPreferences().getEnum<SongSortBy>(
            SettingsKeys.lastUsedPlaylistSongsSortBy,
            null
        )

    fun setLastUsedPlaylistSongsSortBy(sortBy: SongSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedPlaylistSongsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedPlaylistSongsSortBy)
    }

    fun getLastUsedPlaylistSongsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedPlaylistSongsSortReverse, false)

    fun setLastUsedPlaylistSongsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedPlaylistSongsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedPlaylistSongsSortReverse)
    }

    fun getLastUsedAlbumSongsSortBy() =
        getSharedPreferences().getEnum<SongSortBy>(
            SettingsKeys.lastUsedAlbumSongsSortBy,
            null
        )

    fun setLastUsedAlbumSongsSortBy(sortBy: SongSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedAlbumSongsSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumSongsSortBy)
    }

    fun getLastUsedAlbumSongsSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedAlbumSongsSortReverse, false)

    fun setLastUsedAlbumSongsSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedAlbumSongsSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedAlbumSongsSortReverse)
    }

    fun getLastUsedTreePathSortBy() =
        getSharedPreferences().getEnum<PathSortBy>(
            SettingsKeys.lastUsedTreePathSortBy,
            null
        )

    fun setLastUsedTreePathSortBy(sortBy: PathSortBy) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.lastUsedTreePathSortBy, sortBy)
        }
        onChange.dispatch(SettingsKeys.lastUsedTreePathSortBy)
    }

    fun getLastUsedTreePathSortReverse() =
        getSharedPreferences().getBoolean(SettingsKeys.lastUsedTreePathSortReverse, false)

    fun setLastUsedTreePathSortReverse(reverse: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.lastUsedTreePathSortReverse, reverse)
        }
        onChange.dispatch(SettingsKeys.lastUsedTreePathSortReverse)
    }

    fun getPreviousSongQueue() = getSharedPreferences()
        .getString(SettingsKeys.previousSongQueue, null)
        ?.let { RadioQueue.Serialized.parse(it) }

    fun setPreviousSongQueue(queue: RadioQueue.Serialized) {
        getSharedPreferences().edit {
            putString(SettingsKeys.previousSongQueue, queue.serialize())
        }
        onChange.dispatch(SettingsKeys.previousSongQueue)
    }

    fun getHomeLastTab() =
        getSharedPreferences().getEnum(SettingsKeys.homeLastTab, null) ?: HomePages.Songs

    fun setHomeLastTab(value: HomePages) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.homeLastTab, value)
        }
        onChange.dispatch(SettingsKeys.homeLastTab)
    }

    fun getLastDisabledTreePaths(): List<String> =
        getSharedPreferences().getStringSet(SettingsKeys.lastDisabledTreePaths, null)
            ?.toList()
            ?: listOf()

    fun setLastDisabledTreePaths(paths: List<String>) {
        getSharedPreferences().edit {
            putStringSet(SettingsKeys.lastDisabledTreePaths, paths.toSet())
        }
        onChange.dispatch(SettingsKeys.lastDisabledTreePaths)
    }

    fun getSongsFilterPattern() =
        getSharedPreferences().getString(SettingsKeys.songsFilterPattern, null)

    fun setSongsFilterPattern(value: String?) {
        getSharedPreferences().edit {
            putString(SettingsKeys.songsFilterPattern, value)
        }
        onChange.dispatch(SettingsKeys.songsFilterPattern)
    }

    fun getCheckForUpdates() =
        getSharedPreferences().getBoolean(
            SettingsKeys.checkForUpdates,
            SettingsDataDefaults.checkForUpdates,
        )

    fun setCheckForUpdates(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.checkForUpdates, value)
        }
        onChange.dispatch(SettingsKeys.checkForUpdates)
    }

    fun getFadePlayback() =
        getSharedPreferences().getBoolean(
            SettingsKeys.fadePlayback,
            SettingsDataDefaults.fadePlayback,
        )

    fun setFadePlayback(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.fadePlayback, value)
        }
        onChange.dispatch(SettingsKeys.fadePlayback)
    }

    fun getRequireAudioFocus() =
        getSharedPreferences().getBoolean(
            SettingsKeys.requireAudioFocus,
            SettingsDataDefaults.requireAudioFocus,
        )

    fun setRequireAudioFocus(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.requireAudioFocus, value)
        }
        onChange.dispatch(SettingsKeys.requireAudioFocus)
    }

    fun getIgnoreAudioFocusLoss() =
        getSharedPreferences().getBoolean(
            SettingsKeys.ignoreAudioFocusLoss,
            SettingsDataDefaults.ignoreAudioFocusLoss,
        )

    fun setIgnoreAudioFocusLoss(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.ignoreAudioFocusLoss, value)
        }
        onChange.dispatch(SettingsKeys.ignoreAudioFocusLoss)
    }

    fun getPlayOnHeadphonesConnect() =
        getSharedPreferences().getBoolean(
            SettingsKeys.playOnHeadphonesConnect,
            SettingsDataDefaults.playOnHeadphonesConnect,
        )

    fun setPlayOnHeadphonesConnect(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.playOnHeadphonesConnect, value)
        }
        onChange.dispatch(SettingsKeys.playOnHeadphonesConnect)
    }

    fun getPauseOnHeadphonesDisconnect() =
        getSharedPreferences().getBoolean(
            SettingsKeys.pauseOnHeadphonesDisconnect,
            SettingsDataDefaults.pauseOnHeadphonesDisconnect,
        )

    fun setPauseOnHeadphonesDisconnect(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.pauseOnHeadphonesDisconnect, value)
        }
        onChange.dispatch(SettingsKeys.pauseOnHeadphonesDisconnect)
    }

    fun getPrimaryColor() =
        getSharedPreferences().getString(SettingsKeys.primaryColor, null)

    fun setPrimaryColor(value: String) {
        getSharedPreferences().edit {
            putString(SettingsKeys.primaryColor, value)
        }
        onChange.dispatch(SettingsKeys.primaryColor)
    }

    fun getFadePlaybackDuration() =
        getSharedPreferences().getFloat(
            SettingsKeys.fadePlaybackDuration,
            SettingsDataDefaults.fadePlaybackDuration,
        )

    fun setFadePlaybackDuration(value: Float) {
        getSharedPreferences().edit {
            putFloat(SettingsKeys.fadePlaybackDuration, value)
        }
        onChange.dispatch(SettingsKeys.fadePlaybackDuration)
    }

    fun getHomeTabs() = getSharedPreferences()
        .getString(SettingsKeys.homeTabs, null)
        ?.split(",")
        ?.mapNotNull { parseEnumValue<HomePages>(it) }
        ?.toSet()
        ?: SettingsDataDefaults.homeTabs

    fun setHomeTabs(tabs: Set<HomePages>) {
        getSharedPreferences().edit {
            putString(SettingsKeys.homeTabs, tabs.joinToString(",") { it.name })
        }
        onChange.dispatch(SettingsKeys.homeTabs)
        if (getHomeLastTab() !in tabs) {
            setHomeLastTab(tabs.first())
        }
    }

    fun getHomePageBottomBarLabelVisibility() =
        getSharedPreferences()
            .getEnum(SettingsKeys.homePageBottomBarLabelVisibility, null)
            ?: SettingsDataDefaults.homePageBottomBarLabelVisibility

    fun setHomePageBottomBarLabelVisibility(value: HomePageBottomBarLabelVisibility) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.homePageBottomBarLabelVisibility, value)
        }
        onChange.dispatch(SettingsKeys.homePageBottomBarLabelVisibility)
    }

    fun getForYouContents() = getSharedPreferences()
        .getString(SettingsKeys.forYouContents, null)
        ?.split(",")
        ?.mapNotNull { parseEnumValue<ForYou>(it) }
        ?.toSet()
        ?: SettingsDataDefaults.forYouContents

    fun setForYouContents(forYouContents: Set<ForYou>) {
        getSharedPreferences().edit {
            putString(SettingsKeys.forYouContents, forYouContents.joinToString(",") { it.name })
        }
        onChange.dispatch(SettingsKeys.forYouContents)
    }

    fun getBlacklistFolders() = getSharedPreferences()
        .getStringSet(SettingsKeys.blacklistFolders, null)
        ?.toSet<String>()
        ?: SettingsDataDefaults.blacklistFolders

    fun setBlacklistFolders(values: Set<String>) {
        getSharedPreferences().edit {
            putStringSet(SettingsKeys.blacklistFolders, values)
        }
        onChange.dispatch(SettingsKeys.blacklistFolders)
    }

    fun getWhitelistFolders() =
        getSharedPreferences()
            .getStringSet(SettingsKeys.whitelistFolders, null)
            ?.toSet<String>()
            ?: SettingsDataDefaults.whitelistFolders

    fun setWhitelistFolders(values: Set<String>) {
        getSharedPreferences().edit {
            putStringSet(SettingsKeys.whitelistFolders, values)
        }
        onChange.dispatch(SettingsKeys.whitelistFolders)
    }

    fun getReadIntroductoryMessage() = getSharedPreferences().getBoolean(
        SettingsKeys.readIntroductoryMessage,
        SettingsDataDefaults.readIntroductoryMessage,
    )

    fun setReadIntroductoryMessage(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.readIntroductoryMessage, value)
        }
        onChange.dispatch(SettingsKeys.readIntroductoryMessage)
    }

    fun getNowPlayingAdditionalInfo() = getSharedPreferences().getBoolean(
        SettingsKeys.nowPlayingAdditionalInfo,
        SettingsDataDefaults.showNowPlayingAdditionalInfo,
    )

    fun showNowPlayingAdditionalInfo(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.nowPlayingAdditionalInfo, value)
        }
        onChange.dispatch(SettingsKeys.nowPlayingAdditionalInfo)
    }

    fun getNowPlayingSeekControls() = getSharedPreferences().getBoolean(
        SettingsKeys.nowPlayingSeekControls,
        SettingsDataDefaults.enableSeekControls,
    )

    fun setNowPlayingSeekControls(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.nowPlayingSeekControls, value)
        }
        onChange.dispatch(SettingsKeys.nowPlayingSeekControls)
    }

    fun getSeekBackDuration() = getSharedPreferences().getInt(
        SettingsKeys.seekBackDuration,
        SettingsDataDefaults.seekBackDuration,
    )

    fun setSeekBackDuration(value: Int) {
        getSharedPreferences().edit {
            putInt(SettingsKeys.seekBackDuration, value)
        }
        onChange.dispatch(SettingsKeys.seekBackDuration)
    }

    fun getSeekForwardDuration() =
        getSharedPreferences().getInt(
            SettingsKeys.seekForwardDuration,
            SettingsDataDefaults.seekForwardDuration,
        )

    fun setSeekForwardDuration(value: Int) {
        getSharedPreferences().edit {
            putInt(SettingsKeys.seekForwardDuration, value)
        }
        onChange.dispatch(SettingsKeys.seekForwardDuration)
    }

    fun getMiniPlayerTrackControls() =
        getSharedPreferences().getBoolean(
            SettingsKeys.miniPlayerTrackControls,
            SettingsDataDefaults.miniPlayerTrackControls,
        )

    fun setMiniPlayerTrackControls(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.miniPlayerTrackControls, value)
        }
        onChange.dispatch(SettingsKeys.miniPlayerTrackControls)
    }

    fun getMiniPlayerSeekControls() = getSharedPreferences().getBoolean(
        SettingsKeys.miniPlayerSeekControls,
        SettingsDataDefaults.miniPlayerSeekControls,
    )

    fun setMiniPlayerSeekControls(value: Boolean) {
        getSharedPreferences().edit {
            putBoolean(SettingsKeys.miniPlayerSeekControls, value)
        }
        onChange.dispatch(SettingsKeys.miniPlayerSeekControls)
    }

    fun getFontFamily() = getSharedPreferences().getString(SettingsKeys.fontFamily, null)
    fun setFontFamily(language: String) {
        getSharedPreferences().edit {
            putString(SettingsKeys.fontFamily, language)
        }
        onChange.dispatch(SettingsKeys.fontFamily)
    }

    fun getNowPlayingControlsLayout() = getSharedPreferences()
        .getEnum(SettingsKeys.nowPlayingControlsLayout, null)
        ?: SettingsDataDefaults.nowPlayingControlsLayout

    fun setNowPlayingControlsLayout(controlsLayout: NowPlayingControlsLayout) {
        getSharedPreferences().edit {
            putEnum(SettingsKeys.nowPlayingControlsLayout, controlsLayout)
        }
        onChange.dispatch(SettingsKeys.nowPlayingControlsLayout)
    }

    private fun getSharedPreferences() = symphony.applicationContext.getSharedPreferences(
        SettingsKeys.identifier,
        Context.MODE_PRIVATE,
    )
}

private inline fun <reified T : Enum<T>> SharedPreferences.getEnum(
    key: String,
    defaultValue: T?
): T? {
    var result = defaultValue
    getString(key, null)?.let { value ->
        result = parseEnumValue<T>(value)
    }
    return result
}

private inline fun <reified T : Enum<T>> SharedPreferences.Editor.putEnum(
    key: String,
    value: T?
) = putString(key, value?.name)

private inline fun <reified T : Enum<T>> parseEnumValue(value: String): T? =
    T::class.java.enumConstants?.find { it.name == value }
