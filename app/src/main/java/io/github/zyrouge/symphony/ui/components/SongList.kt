package io.github.zyrouge.symphony.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.zyrouge.symphony.services.groove.GrooveKinds
import io.github.zyrouge.symphony.services.groove.Song
import io.github.zyrouge.symphony.services.groove.SongRepository
import io.github.zyrouge.symphony.services.groove.SongSortBy
import io.github.zyrouge.symphony.services.radio.Radio
import io.github.zyrouge.symphony.ui.helpers.ViewContext

enum class SongListType {
    Default,
    Playlist,
    Album,
}

@Composable
fun SongList(
    context: ViewContext,
    songs: List<Song>,
    songsCount: Int? = null,
    leadingContent: (LazyListScope.() -> Unit)? = null,
    trailingContent: (LazyListScope.() -> Unit)? = null,
    trailingOptionsContent: (@Composable ColumnScope.(Int, Song, () -> Unit) -> Unit)? = null,
    type: SongListType = SongListType.Default,
    disableHeartIcon: Boolean = false,
) {
    var sortBy by remember {
        mutableStateOf(type.getLastUsedSortBy(context))
    }
    var sortReverse by remember {
        mutableStateOf(type.getLastUsedSortReverse(context))
    }
    val sortedSongs by remember {
        derivedStateOf { SongRepository.sort(songs, sortBy, sortReverse) }
    }

    MediaSortBarScaffold(
        mediaSortBar = {
            MediaSortBar(
                context,
                reverse = sortReverse,
                onReverseChange = {
                    sortReverse = it
                    type.setLastUsedSortReverse(context, it)
                },
                sort = sortBy,
                sorts = SongSortBy.values().associateWith { x -> { x.label(it) } },
                onSortChange = {
                    sortBy = it
                    type.setLastUsedSortBy(context, it)
                },
                label = {
                    Text(context.symphony.t.XSongs((songsCount ?: songs.size).toString()))
                },
                onShufflePlay = {
                    context.symphony.radio.shorty.playQueue(sortedSongs, shuffle = true)
                }
            )
        },
        content = {
            when {
                songs.isEmpty() -> IconTextBody(
                    icon = { modifier ->
                        Icon(
                            Icons.Default.MusicNote,
                            null,
                            modifier = modifier,
                        )
                    },
                    content = { Text(context.symphony.t.DamnThisIsSoEmpty) }
                )
                else -> {
                    val lazyListState = rememberLazyListState()

                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.drawScrollBar(lazyListState)
                    ) {
                        leadingContent?.invoke(this)
                        itemsIndexed(
                            sortedSongs,
                            key = { i, x -> "$i-${x.id}" },
                            contentType = { _, _ -> GrooveKinds.SONG }
                        ) { i, song ->
                            SongCard(
                                context,
                                song = song,
                                disableHeartIcon = disableHeartIcon,
                                trailingOptionsContent = trailingOptionsContent?.let {
                                    { onDismissRequest -> it(i, song, onDismissRequest) }
                                },
                            ) {
                                context.symphony.radio.shorty.playQueue(
                                    sortedSongs,
                                    Radio.PlayOptions(index = i)
                                )
                            }
                        }
                        trailingContent?.invoke(this)
                    }
                }
            }
        }
    )
}

fun SongSortBy.label(context: ViewContext) = when (this) {
    SongSortBy.CUSTOM -> context.symphony.t.Custom
    SongSortBy.TITLE -> context.symphony.t.Title
    SongSortBy.ARTIST -> context.symphony.t.Artist
    SongSortBy.ALBUM -> context.symphony.t.Album
    SongSortBy.DURATION -> context.symphony.t.Duration
    SongSortBy.DATE_ADDED -> context.symphony.t.DateAdded
    SongSortBy.DATE_MODIFIED -> context.symphony.t.LastModified
    SongSortBy.COMPOSER -> context.symphony.t.Composer
    SongSortBy.ALBUM_ARTIST -> context.symphony.t.AlbumArtist
    SongSortBy.YEAR -> context.symphony.t.Year
    SongSortBy.FILENAME -> context.symphony.t.Filename
    SongSortBy.TRACK_NUMBER -> context.symphony.t.TrackNumber
}

fun SongListType.getLastUsedSortBy(context: ViewContext) = when (this) {
    SongListType.Default -> context.symphony.settings.getLastUsedSongsSortBy() ?: SongSortBy.TITLE
    SongListType.Album -> context.symphony.settings.getLastUsedAlbumSongsSortBy()
        ?: SongSortBy.TITLE
    SongListType.Playlist -> context.symphony.settings.getLastUsedPlaylistSongsSortBy()
        ?: SongSortBy.CUSTOM
}

fun SongListType.setLastUsedSortBy(context: ViewContext, sort: SongSortBy) {
    when (this) {
        SongListType.Default -> context.symphony.settings.setLastUsedSongsSortBy(sort)
        SongListType.Playlist -> context.symphony.settings.setLastUsedPlaylistSongsSortBy(sort)
        SongListType.Album -> context.symphony.settings.setLastUsedAlbumSongsSortBy(sort)
    }
}

fun SongListType.getLastUsedSortReverse(context: ViewContext) = when (this) {
    SongListType.Default -> context.symphony.settings.getLastUsedSongsSortReverse()
    SongListType.Playlist -> context.symphony.settings.getLastUsedPlaylistSongsSortReverse()
    SongListType.Album -> context.symphony.settings.getLastUsedAlbumSongsSortReverse()
}

fun SongListType.setLastUsedSortReverse(context: ViewContext, reverse: Boolean) {
    when (this) {
        SongListType.Default -> context.symphony.settings.setLastUsedSongsSortReverse(reverse)
        SongListType.Playlist -> context.symphony.settings.setLastUsedPlaylistSongsSortReverse(
            reverse
        )
        SongListType.Album -> context.symphony.settings.setLastUsedAlbumSongsSortReverse(reverse)
    }
}
