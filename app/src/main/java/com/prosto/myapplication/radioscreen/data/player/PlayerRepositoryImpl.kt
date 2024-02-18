package com.prosto.myapplication.radioscreen.data.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.prosto.myapplication.core.utils.ConfigTool
import com.prosto.myapplication.radioscreen.domain.player.api.PlayerRepository
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerState
import com.prosto.myapplication.radioscreen.domain.player.models.PlayerStateObserver

class PlayerRepositoryImpl(context: Context) : PlayerRepository {
    private var mediaPlayer: MediaPlayer? = null
    private var streamUrl: String
    private var playerState: PlayerState = PlayerState.STATE_DEFAULT
    private val observers = mutableListOf<PlayerStateObserver>()

    init {
        ConfigTool.init(context)
        streamUrl = ConfigTool.getAppConfig().streamUrl
    }

    override fun getPlayerState(observer: PlayerStateObserver) {
        observers.add(observer)
        observer.onPlayerStateChanged(playerState)
    }

    override fun preparePlayer(callback: (Boolean) -> Unit) {
        try {
            mediaPlayer = MediaPlayer()
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            mediaPlayer?.setAudioAttributes(attributes)
            Log.i("MyLog", "streamUrl = $streamUrl")
            mediaPlayer?.setDataSource(streamUrl)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                playerState = PlayerState.STATE_PREPARED
                notifyPlayerStateChanged(playerState)
                callback(true)
            }
            mediaPlayer?.setOnCompletionListener {
                playerState = PlayerState.STATE_PREPARED
                notifyPlayerStateChanged(playerState)
                callback(false)
            }
            startPlayer()
        } catch (e: Exception) {
            callback(false)
        }
    }

    override fun pausePlayer() {
        if (playerState == PlayerState.STATE_PLAYING) {
            mediaPlayer?.pause()
            playerState = PlayerState.STATE_PAUSED
            notifyPlayerStateChanged(playerState)
        }
    }

    override fun startPlayer() {
        try {
            if (playerState == PlayerState.STATE_PREPARED && mediaPlayer?.isPlaying != true) {
                mediaPlayer?.start()
                playerState = PlayerState.STATE_PLAYING
                notifyPlayerStateChanged(playerState)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun releasePlayer() {
        mediaPlayer?.release()
    }

    private fun notifyPlayerStateChanged(state: PlayerState) {
        playerState = state
        observers.forEach { it.onPlayerStateChanged(state) }
    }
}