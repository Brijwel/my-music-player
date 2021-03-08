package com.brijwel.mymusicplayer.data.local

import androidx.recyclerview.widget.DiffUtil

data class MediaItemData(
    val mediaId: String,
    val title: String,
    val subtitle: String,
    val mediaUri: String,
    val iconUri: String
) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MediaItemData>() {
            override fun areItemsTheSame(oldItem: MediaItemData, newItem: MediaItemData): Boolean =
                oldItem.mediaId == newItem.mediaId

            override fun areContentsTheSame(
                oldItem: MediaItemData,
                newItem: MediaItemData
            ): Boolean = oldItem == newItem

        }
    }
}

