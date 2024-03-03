package com.prosto.ruskoradio.radioscreen.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.exoplayer.ExoPlayer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.prosto.extensions.applyBlurEffect
import com.prosto.extensions.clearBlurEffect
import com.prosto.extensions.vibrateShot
import com.prosto.itunesservice.domain.models.Track
import com.prosto.presentation.BaseFragment
import com.prosto.ruskoradio.R
import com.prosto.ruskoradio.databinding.FragmentRadioBinding
import com.prosto.ruskoradio.main.NotificationService
import com.prosto.ruskoradio.radioscreen.domain.TrackState
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
    private val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun initViews() {
        notifyService = NotificationService(requireContext())
        binding.exoPlayer.player = exoPlayer
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trackState.collect { state ->
                renderTrackCover(state)
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
            PlayerState.STATE_PLAYING -> setPlayStatus()
            PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED, PlayerState.STATE_DEFAULT -> setPauseStatus()
            PlayerState.STATE_LOADING -> setLoadingStatus()
        }
    }

    private fun setPlayStatus() = with(binding) {
        lottieProgressbar.visibility = View.GONE
        btnPlayback.isClickable = true
        btnPlayback.clearBlurEffect()
        btnPlayback.setStatusPlay()
        notifyService.showNotification()
    }

    private fun setPauseStatus() = with(binding) {
        lottieProgressbar.visibility = View.GONE
        btnPlayback.isClickable = true
        btnPlayback.clearBlurEffect()
        btnPlayback.setStatusPause()
    }

    private fun setLoadingStatus() = with(binding) {
        btnPlayback.isClickable = false
        btnPlayback.applyBlurEffect()
        lottieProgressbar.visibility = View.VISIBLE
    }

    private fun clickListeners() = with(binding) {
        val listener = onClickListener()
        btnEmail.setOnClickListener(listener)
        btnShare.setOnClickListener(listener)
        btnWebsite.setOnClickListener(listener)
        btnPlayback.setOnClickListener(listener)
    }

    private fun onClickListener() = View.OnClickListener {
        with(binding) {
            when (it) {
                btnEmail -> sendEmail()
                btnShare -> shareSong()
                btnWebsite -> visitWebsite()
                btnPlayback -> playBackManager()
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

    private fun renderTrackCover(state: TrackState) {
        when (state) {
            is TrackState.Content -> state.track?.let {
                fetchCover(it)
                fetchBackgroundImg(it)
            }

            else -> binding.ivLogo.setImageResource(R.drawable.logo)
        }
    }

    private fun fetchCover(track: Track) = with(binding) {
        Log.i("MyLog", "track = $track")
        ivLogo.load(track.getArtwork512()) {
            placeholder(R.drawable.logo)
            val density = resources.displayMetrics.density
            val cornerRadiusPx = resources.getDimension(R.dimen.img_corner_8) * density
            transformations(
                RoundedCornersTransformation(
                    cornerRadiusPx
                )
            )
        }
    }

//    private fun fetchBackgroundImg(track: Track) = with(binding) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            ivBackground.load(track.getArtwork512()) {
//                placeholder(R.drawable.background_color)
//            }
//            ivBackground.applyBlurEffect(radius = 512f)
//            ivBackground.clearBlurEffect()
//        }
//    }

    private fun fetchBackgroundImg(track: Track) = with(binding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val placeholderDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_color)
            ivBackground.load(track.getArtwork512()) {
                placeholder(placeholderDrawable)
                listener(onSuccess = { _, _ ->
                    if (placeholderDrawable?.constantState == AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.background_color
                        )?.constantState
                    ) {
                        ivBackground.applyBlurEffect(radius = 512f)
                    } else {
                        ivBackground.clearBlurEffect()
                    }
                })
            }
        }
    }

    companion object {
        private const val TEXT_TYPE = "text/plain"
        private const val VIBRO_SHOT_50MS = 50L
    }
}
