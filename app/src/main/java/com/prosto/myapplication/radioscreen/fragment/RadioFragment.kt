package com.prosto.myapplication.radioscreen.fragment

import android.media.MediaPlayer
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.prosto.myapplication.R
import com.prosto.myapplication.core.ui.BaseFragment
import com.prosto.myapplication.core.utils.applyBlurEffect
import com.prosto.myapplication.core.utils.clearBlurEffect
import com.prosto.myapplication.core.utils.vibrateShot
import com.prosto.myapplication.databinding.FragmentRadioBinding
import com.prosto.myapplication.main.NotificationService
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerState
import com.prosto.myapplication.radioscreen.domain.radio.models.RadioState
import com.prosto.myapplication.radioscreen.viewmodel.RadioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var notifyService : NotificationService

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun initViews() {
        notifyService = NotificationService(requireContext())
        Unit
    }

    override fun subscribe() {
        with(binding) {
            ivPlayPause.setOnClickListener {
                viewModel.playbackControl()
                requireContext().vibrateShot(50)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.playerState.collect { state ->
                    when (state) {
                        PlayerState.STATE_PLAYING -> {
                            ivPlayPause.isEnabled = true
                            ivPlayPause.clearBlurEffect()
                            lottieProgressbar.visibility = View.GONE
                            showPauseButton()
                            notifyService.showNotification()
                        }

                        PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED, PlayerState.STATE_DEFAULT -> {
                            ivPlayPause.isEnabled = true
                            ivPlayPause.clearBlurEffect()
                            lottieProgressbar.visibility = View.GONE
                            showPlayButton()
                        }

                        PlayerState.STATE_LOADING -> {
                            ivPlayPause.isEnabled = false
                            ivPlayPause.applyBlurEffect()
                            lottieProgressbar.visibility = View.VISIBLE
                        }
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
                lottieProgressPoints.isVisible = false
                llPlayer.isVisible = true
            }

            is RadioState.ConnectionError -> {
                tvSongTitle.text = requireContext().getString(R.string.internet_connection_error)
                lottieProgressPoints.isVisible = false
                llPlayer.isVisible = true
            }

            is RadioState.Loading -> {
                lottieProgressPoints.isVisible = true
                llPlayer.isVisible = false
                tvSongTitle.text = requireContext().getString(R.string.loading)
            }

            is RadioState.Empty -> {
                lottieProgressPoints.isVisible = false
                llPlayer.isVisible = true
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
