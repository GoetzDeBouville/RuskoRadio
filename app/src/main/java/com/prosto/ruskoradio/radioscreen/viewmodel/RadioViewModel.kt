package com.prosto.ruskoradio.radioscreen.viewmodel

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellcorp.restquest.domain.network.models.LoadingStatus
import com.prosto.itunesservice.domain.api.ItunesInteractor
import com.prosto.itunesservice.domain.models.LoadingTrackStatus
import com.prosto.itunesservice.domain.models.Track
import com.prosto.ruskoradio.core.utils.ConfigTool
import com.prosto.ruskoradio.radioscreen.domain.TrackState
import com.prosto.ruskoradio.radioscreen.domain.player.models.PlayerState
import com.prosto.ruskoradio.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.ruskoradio.radioscreen.domain.radio.models.RadioState
import com.prosto.ruskoradio.radioscreen.dto.SongEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val radioInteractor: RadioInteractor,
    private val itunesInteractor: ItunesInteractor
) : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private var streamUrl = ""
    private var track: Track? = null
    private var expression = ""
    private val _radioState = MutableStateFlow<RadioState>(RadioState.Loading)
    val radioState: StateFlow<RadioState>
        get() = _radioState

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.STATE_PAUSED)
    val playerState: StateFlow<PlayerState>
        get() = _playerState

    private val _trackState = MutableStateFlow<TrackState>(TrackState.Empty)
    val trackState: StateFlow<TrackState>
        get() = _trackState

    init {
        getSongTitle()
        ConfigTool.init(context)
        streamUrl = ConfigTool.getAppConfig().streamUrl
        preparePlayer()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    private fun getSongTitle() {
        viewModelScope.launch {
            while (true) {
                radioInteractor.updateTitle().collect { result ->
                    processRadioState(result)
                    delay(DELAY_3000_MS)
                }
            }
        }
    }

    private fun getFirstTrack(expression: String?) {
        viewModelScope.launch {
            expression?.let {
                itunesInteractor.getFirstTrack(expression).collect {
                    itunesResult(it)
                }
            }
        }
    }

    private fun itunesResult(result: Pair<Track?, LoadingTrackStatus?>) {
        Log.i("MyLog", "itunesResult = $result")
        if (result.second == null) {
            _trackState.value = TrackState.Content(track = result.first)
        } else {
            _trackState.value = TrackState.ConnectionError
        }
    }

    private fun processRadioState(result: Pair<SongEntity?, LoadingStatus?>) {
        if (result.second == null) {
            _radioState.value = RadioState.Content(result.first!!)
            Log.i("MyLog", "title = ${result.first!!.title.lowercase()}")
            if (expression != result.first!!.title.lowercase() && !result.first!!.title.lowercase().contains(IGNORE_EXPRESSION)) {
                getFirstTrack(result.first!!.title)
                expression = result.first!!.title.lowercase()
            }
        } else {
            _radioState.value = RadioState.ConnectionError
        }
    }

    private fun preparePlayer() {
        releasePlayer()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(streamUrl)
            prepareAsync()
            setOnPreparedListener {
                _playerState.value = PlayerState.STATE_PREPARED
                startPlayer()
            }
        }
        _playerState.value = PlayerState.STATE_LOADING
    }

    fun playbackControl() {
        when (_playerState.value) {
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }

            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED, PlayerState.STATE_DEFAULT -> {
                preparePlayer()
            }

            else -> Unit
        }
    }

    private fun startPlayer() {
        mediaPlayer?.start()
        _playerState.value = PlayerState.STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer?.pause()
        _playerState.value = PlayerState.STATE_PAUSED
        releasePlayer()
    }

    private fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        _playerState.value = PlayerState.STATE_DEFAULT
    }

    companion object {
        private const val DELAY_3000_MS = 3000L
        private const val IGNORE_EXPRESSION = "rusko radio"
    }
}