package com.brijwel.mymusicplayer.ui

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.brijwel.mymusicplayer.api.Resource
import com.brijwel.mymusicplayer.data.local.MediaItemData
import com.brijwel.mymusicplayer.data.remote.Music
import com.brijwel.mymusicplayer.exoplayer.MusicServiceConnection
import com.brijwel.mymusicplayer.exoplayer.isPlayEnabled
import com.brijwel.mymusicplayer.exoplayer.isPlaying
import com.brijwel.mymusicplayer.exoplayer.isPrepared
import com.brijwel.mymusicplayer.repo.MusicRepo
import com.brijwel.mymusicplayer.util.Constant
import kotlinx.coroutines.flow.flow

/**
 * Created by Brijwel on 07-03-2021.
 */
private const val TAG = "MusicListViewModel"

class MusicListViewModel(
    private val musicServiceConnection: MusicServiceConnection,
    private val musicRepo: MusicRepo
) : ViewModel() {
    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        musicServiceConnection.subscribe(
            Constant.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val medias = children.map {
                        Log.d(TAG, "onChildrenLoaded: ${it.description.title}")
                        MediaItemData(
                            it.mediaId!!,
                            it.description.title.toString(),
                            it.description.subtitle.toString(),
                            it.description.mediaUri.toString(),
                            it.description.iconUri.toString()
                        )
                    }
                }
            })
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }

    fun stopMusic() {
        musicServiceConnection.transportControls.seekTo(0L)
        musicServiceConnection.transportControls.pause()
    }

    fun rewindMusic() {
        musicServiceConnection.transportControls.rewind()
    }

    fun playOrToggleSong(mediaItem: Music, toggle: Boolean = false) {
        Log.d(TAG, "playOrToggleSong: ")
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.id ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        ) {
            Log.d(TAG, "playOrToggleSong: isPrepared")
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            Log.d(TAG, "playOrToggleSong: not prepared")
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id, null)
        }
    }

    fun playOrToggleSong(mediaId: String, toggle: Boolean = false) {
        Log.d(TAG, "playOrToggleSong: ")
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaId ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        ) {
            Log.d(TAG, "playOrToggleSong: isPrepared")
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            Log.d(TAG, "playOrToggleSong: not prepared")
            musicServiceConnection.transportControls.playFromMediaId(mediaId, null)
        }
    }


    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(
            Constant.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})

    }

    fun getMusic() = flow {
        emit(Resource.loading(null))
        try {
            val response = musicRepo.getMusics()
            emit(Resource.success(response.music))
        } catch (e: Exception) {
            emit(Resource.error(e.message.toString(), null))
        }
    }.asLiveData()
}