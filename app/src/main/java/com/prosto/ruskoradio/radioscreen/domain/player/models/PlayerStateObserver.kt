package com.prosto.ruskoradio.radioscreen.domain.player.models

interface PlayerStateObserver {
    fun onPlayerStateChanged(state: PlayerState)
}
