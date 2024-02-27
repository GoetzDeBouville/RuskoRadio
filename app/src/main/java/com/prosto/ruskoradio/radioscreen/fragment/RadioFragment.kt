package com.prosto.ruskoradio.radioscreen.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.hellcorp.presentation.BaseFragment
import com.prosto.ruskoradio.R
import com.prosto.ruskoradio.core.utils.applyBlurEffect
import com.prosto.ruskoradio.core.utils.clearBlurEffect
import com.prosto.ruskoradio.core.utils.vibrateShot
import com.prosto.ruskoradio.databinding.FragmentRadioBinding
import com.prosto.ruskoradio.main.NotificationService
import com.prosto.ruskoradio.radioscreen.domain.player.models.PlayerState
import com.prosto.ruskoradio.radioscreen.domain.radio.models.RadioState
import com.prosto.ruskoradio.radioscreen.viewmodel.RadioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var notifyService: NotificationService
    private var songTitle = ""

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun initViews() {
        notifyService = NotificationService(requireContext())
    }

    override fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playerState.collect { state ->
                renderPlayerState((state))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.radioState.collect { state ->
                renderSongTitleState(state)
            }
        }
        clickListeners()
    }

    private fun renderSongTitleState(state: RadioState) = with(binding) {
        when (state) {
            is RadioState.Content -> {
                tvSongTitle.text = state.songEntity.title
                songTitle = state.songEntity.title
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

    private fun renderPlayerState(state: PlayerState) = with(binding) {
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

    private fun clickListeners() = with(binding) {
        val listener = onClickListener()
        btnEmail.setOnClickListener(listener)
        btnShare.setOnClickListener(listener)
        btnWebsite.setOnClickListener(listener)
        ivPlayPause.setOnClickListener(listener)
    }

    private fun onClickListener() = View.OnClickListener {
        with(binding) {
            when (it) {
                btnEmail -> sendEmail()
                btnShare -> shareSong()
                btnWebsite -> visitWebsite()
                ivPlayPause -> playBackManager()
            }
        }
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(getString(R.string.email_intent))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback))
        }
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send)))
    }

    private fun shareSong() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = TEXT_TYPE
            if (songTitle.isEmpty()) {
                putExtra(Intent.EXTRA_TEXT, getString(R.string.listen_to_the_radio_invitation))
            } else {
                putExtra(Intent.EXTRA_TEXT, getString(R.string.listen_to_the_song, songTitle))
            }
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }

    private fun visitWebsite() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.website))))
    }

    private fun playBackManager() {
        viewModel.playbackControl()
        requireContext().vibrateShot(VIBRO_SHOT_50MS)
    }

//    private fun playBackManager() {
//        binding.ivPlayPause.setOnClickListener {
//            viewModel.playbackControl()
//            requireContext().vibrateShot(VIBRO_SHOT_50MS)
//        }
//    }

    companion object {
        private const val TEXT_TYPE = "text/plain"
        private const val VIBRO_SHOT_50MS = 50L
    }
}
