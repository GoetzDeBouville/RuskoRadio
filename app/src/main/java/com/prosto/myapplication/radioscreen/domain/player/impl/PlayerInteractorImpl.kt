package com.prosto.myapplication.radioscreen.domain.player.impl

import android.media.MediaPlayer
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerState
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerStateObserver
import com.prosto.myapplication.radioscreen.domain.player.api.PlayerInteractor

class PlayerInteractorImpl : PlayerInteractor {
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackTime: Long = 0L
    private var startTime: Long = 0L
    private var playerState: PlayerState = PlayerState.STATE_DEFAULT
    private val observers = mutableListOf<PlayerStateObserver>()

//    override fun getPlayerState(observer: PlayerStateObserver) {
//    }

    override fun pausePlayer() {
    }

    override fun startPlayer() {
    }

    override fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun updatePlayerState(state: PlayerState) {
        playerState = state
        observers.forEach { it.onPlayerStateChanged(state) }
    }
}