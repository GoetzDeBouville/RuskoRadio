package com.prosto.myapplication.radioscreen.domain.player.api

import com.prosto.myapplication.radioscreen.domain.player.models.PlayerStateObserver

interface PlayerRepository {
    fun getPlayerState(observer: PlayerStateObserver)
    fun preparePlayer(callback: (Boolean) -> Unit)
    fun pausePlayer()
    fun startPlayer()
    fun releasePlayer()
}