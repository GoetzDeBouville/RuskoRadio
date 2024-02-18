package com.prosto.myapplication.radioscreen.fragment

import android.media.MediaPlayer
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.prosto.myapplication.R
import com.prosto.myapplication.core.ui.BaseFragment
import com.prosto.myapplication.core.utils.applyBlurEffect
import com.prosto.myapplication.core.utils.clearBlurEffect
import com.prosto.myapplication.core.utils.debounce
import com.prosto.myapplication.core.utils.vibrateShot
import com.prosto.myapplication.databinding.FragmentRadioBinding
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerState
import com.prosto.myapplication.radioscreen.domain.radio.models.RadioState
import com.prosto.myapplication.radioscreen.viewmodel.RadioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()
    private var isPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private var streamUrl: String = ""

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun initViews() {
    }

    override fun subscribe() {
        binding.ivPlayPause.setOnClickListener {
            viewModel.playbackControl()
            requireContext().vibrateShot(300)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playerState.collect { state ->
                Log.i("MyLog", "playerState = $state")
                when (state) {
                    PlayerState.STATE_PLAYING -> {
                        binding.ivPlayPause.isEnabled = true
                        binding.lottieProgressbar.visibility = View.GONE
                        showPauseButton()
                    }

                    PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED, PlayerState.STATE_DEFAULT -> {
                        binding.ivPlayPause.isEnabled = true
                        binding.lottieProgressbar.visibility = View.GONE
                        showPlayButton()
                    }

                    PlayerState.STATE_LOADING -> {
                        binding.ivPlayPause.isEnabled = false
                        binding.lottieProgressbar.visibility = View.VISIBLE

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.radioState.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: RadioState) = with(binding) {
        when (state) {
            is RadioState.Content -> {
                tvSongTitle.text = state.songEntity.title
            }

            is RadioState.ConnectionError -> {
                tvSongTitle.text = requireContext().getString(R.string.internet_connection_error)
            }

            is RadioState.Loading -> {
                tvSongTitle.text = requireContext().getString(R.string.loading)
            }

            is RadioState.Empty -> {
                tvSongTitle.text = ""
            }
        }
    }

    private fun applyBackgroundBlur() = with(binding) {
        ivPlayPause.applyBlurEffect()
    }

    private fun clearBackgroundBlur() = with(binding) {
        ivPlayPause.clearBlurEffect()
    }

    private fun showPlayButton() {
        binding.ivPlayPause.setImageResource(R.drawable.play_circle)
    }

    private fun showPauseButton() {
        binding.ivPlayPause.setImageResource(R.drawable.pause_circle)
    }
}
