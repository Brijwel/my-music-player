package com.brijwel.mymusicplayer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.api.Music
import com.brijwel.mymusicplayer.databinding.ItemMusicBinding

/**
 * Created by Brijwel on 07-03-2021.
 */
class MusicAdapter(private val onClick: (music: Music) -> Unit) :
    ListAdapter<Music, MusicAdapter.MusicViewHolder>(Music.DiffUtil) {
    inner class MusicViewHolder(private val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition)?.let {
                    onClick(it)
                }
            }

        }

        fun bind(music: Music) {
            binding.music = music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = DataBindingUtil.inflate<ItemMusicBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_music,
            parent,
            false
        )
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}