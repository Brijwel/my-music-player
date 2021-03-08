package com.brijwel.mymusicplayer.ui

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.api.Status
import com.brijwel.mymusicplayer.databinding.FragmentMusicListBinding
import com.brijwel.mymusicplayer.exoplayer.isPlaying
import com.brijwel.mymusicplayer.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Brijwel on 07-03-2021.
 */
private const val TAG = "MusicListFragment"

class MusicListFragment : Fragment(R.layout.fragment_music_list) {
    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicListViewModel by viewModel()

    private val musicAdapter = MusicAdapter { music ->
        findNavController().navigate(
            R.id.nav_now_playing,
            bundleOf(Constant.SELECTED_MEDIA to music.id)
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMusicListBinding.bind(view)

        binding.musicRecyclerView.adapter = musicAdapter

        viewModel.getMusic().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    musicAdapter.submitList(resource.data)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}