package com.brijwel.mymusicplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brijwel.mymusicplayer.exoplayer.MusicService
import com.brijwel.mymusicplayer.exoplayer.MusicServiceConnection
import com.brijwel.mymusicplayer.exoplayer.currentPlaybackPosition
import com.brijwel.mymusicplayer.util.Constant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Brijwel on 07-03-2021.
 */
class NowPlayingMusicViewModel(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration: LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition: LiveData<Long> = _curPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while(true) {
                val pos = playbackState.value?.currentPlaybackPosition
                if(curPlayerPosition.value != pos) {
                    _curPlayerPosition.postValue(pos)
                    _curSongDuration.postValue(MusicService.curSongDuration)
                }
                delay(Constant.UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}