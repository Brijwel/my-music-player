package com.brijwel.mymusicplayer.ui

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.api.Status
import com.brijwel.mymusicplayer.databinding.BindingAdapter
import com.brijwel.mymusicplayer.databinding.FragmentNowPlayingMusicBinding
import com.brijwel.mymusicplayer.exoplayer.extention.isPlaying
import com.brijwel.mymusicplayer.repo.WaveFormData
import com.brijwel.mymusicplayer.util.Constant
import com.google.android.material.snackbar.Snackbar
import com.masoudss.lib.SeekBarOnProgressChanged
import com.masoudss.lib.WaveformSeekBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

/**
 * Created by Brijwel on 07-03-2021.
 */
class NowPlayingFragment : Fragment(R.layout.fragment_now_playing_music) {
    private var _binding: FragmentNowPlayingMusicBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicListViewModel by viewModel()
    private val nowPlayingMusicViewModel: NowPlayingMusicViewModel by viewModel()
    private var playbackState: PlaybackStateCompat? = null
    private var shouldUpdateSeekBar = true

    private var mediaId = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingMusicBinding.bind(view)
        binding.apply {
            soundWaveProgressBar.sample = WaveFormData.getWaveDate()
            ivBack.setOnClickListener { findNavController().navigateUp() }

            viewModel.isConnected.observe(viewLifecycleOwner) {
                it?.getContentIfNotHandled()?.let { result ->

                    when (result.status) {
                        Status.ERROR -> Snackbar.make(
                            requireView(),
                            result.message ?: "An unknown error occured",
                            Snackbar.LENGTH_LONG
                        ).show()
                        else -> Unit
                    }
                    if (!result.data!!) {
                        findNavController().popBackStack()
                    }
                }
            }

            ivPlayOrPause.setOnClickListener {
                viewModel.playOrToggleSong(mediaId, true)
            }

            ivRestartAudio.setOnClickListener {
                viewModel.rewindMusic()
            }

            imFastBackward.setOnClickListener {
                viewModel.skipToPreviousSong()
            }

            imFastForward.setOnClickListener {
                viewModel.skipToNextSong()
            }
            ivStopAudio.setOnClickListener {
                viewModel.stopMusic()
            }

            viewModel.curPlayingSong.observe(viewLifecycleOwner) {
                it?.let {
                    mediaId = it.description?.mediaId ?: ""
                    updateCurrentSongDetails(it)
                }
            }

            viewModel.playbackState.observe(viewLifecycleOwner) {
                playbackState = it
                ivPlayOrPause.setImageResource(
                    if (playbackState?.isPlaying == true) R.drawable.ic_pause_media else R.drawable.ic_play_media
                )
                soundWaveProgressBar.progress = it?.position?.toInt() ?: 0
                sbAudioProgress.progress = it?.position?.toInt() ?: 0
            }
            nowPlayingMusicViewModel.curPlayerPosition.observe(viewLifecycleOwner) {
                if (shouldUpdateSeekBar) {
                    soundWaveProgressBar.progress = it.toInt()
                    sbAudioProgress.progress = it.toInt()
                    setElapsedTime(it)
                }
            }

            nowPlayingMusicViewModel.curSongDuration.observe(viewLifecycleOwner) {
                soundWaveProgressBar.maxProgress = it.toFloat()
                sbAudioProgress.max = it.toInt()
                setTotalSongTime(it)
            }
            soundWaveProgressBar.onProgressChanged = object : SeekBarOnProgressChanged {
                override fun onProgressChanged(
                    waveformSeekBar: WaveformSeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        sbAudioProgress.progress = progress
                        viewModel.seekTo(progress.toLong())
                        shouldUpdateSeekBar = true
                    }
                }


            }

            sbAudioProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        soundWaveProgressBar.progress = progress
                        setElapsedTime(progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    shouldUpdateSeekBar = false
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        viewModel.seekTo(it.progress.toLong())
                        shouldUpdateSeekBar = true
                    }
                }

            })

        }
    }

    private fun updateCurrentSongDetails(mediaMetadataCompat: MediaMetadataCompat) {
        mediaMetadataCompat.description?.let {
            val subtitle = it.subtitle.toString()
            val title = it.title.toString()
            val iconUri = it.iconUri.toString()
            binding.apply {
                tvMediaCategory.text = subtitle
                tvMediaName.text = title
                BindingAdapter.setImage(albumArt, iconUri)
            }
        }
    }

    private fun setElapsedTime(ms: Long) {

        binding.tvTimeElapsed.text = timeStampToMSS(ms)
    }

    private fun setTotalSongTime(ms: Long) {
        binding.tvTotalTime.text = timeStampToMSS(ms)
    }

    private fun timeStampToMSS(position: Long): String {
        val totalSeconds = floor(position / 1E3).toInt()
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds - (minutes * 60)
        return if (position < 0) requireContext().getString(R.string.duration_unknown)
        else requireContext().getString(R.string.duration_format).format(minutes, remainingSeconds)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mediaId = arguments?.getString(Constant.SELECTED_MEDIA) ?: return
        viewModel.playOrToggleSong(mediaId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}