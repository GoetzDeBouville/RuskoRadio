package com.prosto.myapplication.radioscreen.domain.player.api

interface PlayerInteractor {
//    fun getPlayerState(observer: PlayerStateObserver)
    fun pausePlayer()
    fun startPlayer()
    fun releasePlayer()
}