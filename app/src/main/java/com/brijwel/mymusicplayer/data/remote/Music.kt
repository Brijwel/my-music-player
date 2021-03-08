package com.brijwel.mymusicplayer.data.remote

import androidx.recyclerview.widget.DiffUtil

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
) {
    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<Music>() {
            override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean =
                oldItem == newItem

        }
    }
}
