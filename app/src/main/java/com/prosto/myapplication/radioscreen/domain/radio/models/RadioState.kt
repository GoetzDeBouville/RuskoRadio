package com.prosto.myapplication.radioscreen.domain.radio.models

import com.prosto.myapplication.radioscreen.dto.SongEntity

sealed interface RadioState {
    data class Content(
        val songEntity: SongEntity
    ) : RadioState

    data object Loading : RadioState
    data object Empty : RadioState
    data object ConnectionError : RadioState
}
