package com.prosto.myapplication.radioscreen.domain.player.impl

import com.prosto.myapplication.radioscreen.domain.player.api.PlayerInteractor
import com.prosto.myapplication.radioscreen.domain.player.api.PlayerRepository
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerStateObserver

class PlayerInteractorImpl(
    private val player: PlayerRepository
) : PlayerInteractor {
    override fun getPlayerState(observer: PlayerStateObserver) {
        player.getPlayerState(observer)
    }

    override fun preparePlayer(callback: (Boolean) -> Unit) {
        player.preparePlayer(callback)
    }

    override fun pausePlayer() {
        player.pausePlayer()
    }

    override fun startPlayer() {
        player.startPlayer()
    }

    override fun releasePlayer() {
        player.releasePlayer()
    }
}