package com.prosto.myapplication.radioscreen.domain.player.models

interface PlayerStateObserver {
    fun onPlayerStateChanged(state: PlayerState)
}
