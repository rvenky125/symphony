package io.github.zyrouge.symphony.ui.components

import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.github.zyrouge.symphony.services.groove.Album
import io.github.zyrouge.symphony.services.groove.AlbumRepository
import io.github.zyrouge.symphony.services.groove.AlbumSortBy
import io.github.zyrouge.symphony.services.groove.GrooveKinds
import io.github.zyrouge.symphony.ui.helpers.ViewContext

@Composable
fun AlbumGrid(
    context: ViewContext,
    albums: List<Album>,
    albumsCount: Int? = null,
) {
    var sortBy by remember {
        mutableStateOf(
            context.symphony.settings.getLastUsedAlbumsSortBy() ?: AlbumSortBy.ALBUM_NAME
        )
    }
    var sortReverse by remember {
        mutableStateOf(context.symphony.settings.getLastUsedAlbumsSortReverse())
    }
    val sortedAlbums by remember {
        derivedStateOf { AlbumRepository.sort(albums, sortBy, sortReverse) }
    }

    MediaSortBarScaffold(
        mediaSortBar = {
            MediaSortBar(
                context,
                reverse = sortReverse,
                onReverseChange = {
                    sortReverse = it
                    context.symphony.settings.setLastUsedAlbumsSortReverse(it)
                },
                sort = sortBy,
                sorts = AlbumSortBy.values().associateWith { x -> { x.label(it) } },
                onSortChange = {
                    sortBy = it
                    context.symphony.settings.setLastUsedAlbumsSortBy(it)
                },
                label = {
                    Text(context.symphony.t.XAlbums((albumsCount ?: albums.size).toString()))
                },
            )
        },
        content = {
            when {
                albums.isEmpty() -> IconTextBody(
                    icon = { modifier ->
                        Icon(
                            Icons.Default.Album,
                            null,
                            modifier = modifier,
                        )
                    },
                    content = { Text(context.symphony.t.DamnThisIsSoEmpty) }
                )
                else -> ResponsiveGrid {
                    itemsIndexed(
                        sortedAlbums,
                        key = { i, x -> "$i-${x.id}" },
                        contentType = { _, _ -> GrooveKinds.ALBUM }
                    ) { _, album ->
                        AlbumTile(context, album)
                    }
                }
            }
        }
    )
}

private fun AlbumSortBy.label(context: ViewContext) = when (this) {
    AlbumSortBy.CUSTOM -> context.symphony.t.Custom
    AlbumSortBy.ALBUM_NAME -> context.symphony.t.Album
    AlbumSortBy.ARTIST_NAME -> context.symphony.t.Artist
    AlbumSortBy.TRACKS_COUNT -> context.symphony.t.TrackCount
}
