package com.prosto.myapplication.radioscreen.viewmodel

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.viewModelScope
import com.hellcorp.restquest.domain.network.models.LoadingStatus
import com.prosto.myapplication.core.ui.BaseViewModel
import com.prosto.myapplication.core.utils.ConfigTool
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerState
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.myapplication.radioscreen.domain.radio.models.RadioState
import com.prosto.myapplication.radioscreen.dto.SongEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val radioInteractor: RadioInteractor
) : BaseViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private var streamUrl = ""
    private val _radioState = MutableStateFlow<RadioState>(RadioState.Loading)
    val radioState: StateFlow<RadioState>
        get() = _radioState

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.STATE_PAUSED)
    val playerState: StateFlow<PlayerState>
        get() = _playerState

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
                    processResult(result)
                    delay(DELAY_3000_MS)
                }
            }
        }
    }

    private fun processResult(result: Pair<SongEntity?, LoadingStatus?>) {
        if (result.second == null) {
            _radioState.value = RadioState.Content(result.first!!)
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
    }
}