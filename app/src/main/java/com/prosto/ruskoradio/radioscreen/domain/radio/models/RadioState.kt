package com.prosto.ruskoradio.radioscreen.domain.radio.models

import com.prosto.ruskoradio.radioscreen.dto.SongEntity

sealed interface RadioState {
    data class Content(
        val songEntity: SongEntity
    ) : RadioState

    data object Loading : RadioState
    data object Empty : RadioState
    data object ConnectionError : RadioState
}
