package com.brijwel.mymusicplayer.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.api.Status
import com.brijwel.mymusicplayer.databinding.FragmentMusicListBinding
import com.brijwel.mymusicplayer.util.Constant
import com.google.android.material.snackbar.Snackbar
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
            bundleOf(Constant.SELECTED_MEDIA to music.mediaId)
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMusicListBinding.bind(view)

        binding.musicRecyclerView.adapter = musicAdapter

        viewModel.networkError.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Snackbar.make(
                        requireView(),
                        result.message ?: "An unknown error occured",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
        viewModel.mediaItems.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> Unit
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    musicAdapter.submitList(it.data)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}