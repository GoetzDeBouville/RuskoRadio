package com.prosto.myapplication.radioscreen.domain.player.api

interface PlayerRepository {
//    fun getPlayerState(observer: PlayerStateObserver)
    fun pausePlayer()
    fun startPlayer()
    fun releasePlayer()
}