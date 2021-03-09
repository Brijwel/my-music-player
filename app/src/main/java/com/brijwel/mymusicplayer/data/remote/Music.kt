package com.brijwel.mymusicplayer.data.remote

data class Music(
    val album: String = "",
    val artist: String = "",
    val duration: Int = 0,
    val genre: String = "",
    val id: String,
    val image: String = "",
    val site: String = "",
    val source: String = "",
    val title: String = "",
    val totalTrackCount: Int = 0,
    val trackNumber: Int = 0
)