package com.brijwel.mymusicplayer.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.brijwel.mymusicplayer.api.ApiService
import com.brijwel.mymusicplayer.exoplayer.State.STATE_CREATED
import com.brijwel.mymusicplayer.exoplayer.State.STATE_INITIALIZING
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Brijwel on 07-03-2021.
 */
class MusicSource(private val apiService: ApiService) {
    var musics = emptyList<MediaMetadataCompat>()

    suspend fun fetchMusic() = withContext(Dispatchers.IO) {
        state = STATE_INITIALIZING
        try {
            val musicsFromRemote = apiService.getMusics()
            if (musicsFromRemote.music.isNullOrEmpty()) {
                musics=musicsFromRemote.music.map { music->
                    MediaMetadataCompat.Builder()
                        .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.artist)
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, music.id)
                        .putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.title)
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, music.title)
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, music.image)
                        .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, music.source)
                        .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, music.image)
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, music.album)
                        .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, music.genre)
                        .build()
                }
            }
        } catch (e: Exception) { }

    }
    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        musics.forEach { song ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(song.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItems() = musics.map { music ->
        val desc = MediaDescriptionCompat.Builder()
            .setMediaUri(music.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri())
            .setTitle(music.description.title)
            .setSubtitle(music.description.subtitle)
            .setMediaId(music.description.mediaId)
            .setIconUri(music.description.iconUri)
            .build()
        MediaBrowserCompat.MediaItem(desc, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }.toMutableList()
    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = STATE_CREATED
        set(value) {
            if (value == State.STATE_INITIALIZED || value == State.STATE_ERROR) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener ->
                        listener(state == State.STATE_INITIALIZED)
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean {
        return if (state == STATE_CREATED || state == State.STATE_INITIALIZING) {
            onReadyListeners += action
            false
        } else {
            action(state == State.STATE_INITIALIZED)
            true
        }
    }
}

enum class State {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}





