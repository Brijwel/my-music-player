package com.brijwel.mymusicplayer.api

import com.brijwel.mymusicplayer.data.remote.Music

data class ApiResponse(
    val music: List<Music>
)