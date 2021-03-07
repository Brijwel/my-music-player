package com.brijwel.mymusicplayer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.databinding.FragmentNowPlayingMusicBinding
import com.brijwel.mymusicplayer.repo.WaveFormData

/**
 * Created by Brijwel on 07-03-2021.
 */
class NowPlayingFragment : Fragment(R.layout.fragment_now_playing_music) {
    private var _binding: FragmentNowPlayingMusicBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingMusicBinding.bind(view)
        binding.apply {

            soundWaveProgressBar.sample = WaveFormData.getWaveDate()

            ivBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}