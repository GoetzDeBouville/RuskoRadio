package com.prosto.ruskoradio.radioscreen.domain

import com.prosto.itunesservice.domain.models.Track

sealed interface TrackState{
data class Content(
    val track: Track?
) : TrackState

data object Loading : TrackState
data object Empty : TrackState
data object ConnectionError : TrackState
}
