package com.brijwel.mymusicplayer.ui

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.brijwel.mymusicplayer.api.Music
import com.brijwel.mymusicplayer.api.Resource
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

    fun playOrToggleSong(mediaItem: Music, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.id ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.id, null)
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