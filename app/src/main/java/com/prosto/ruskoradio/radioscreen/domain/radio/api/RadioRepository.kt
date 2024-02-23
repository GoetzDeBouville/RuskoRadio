package com.prosto.ruskoradio.radioscreen.domain.radio.api

import com.hellcorp.restquest.domain.network.models.Resource
import com.prosto.ruskoradio.radioscreen.dto.SongDto
import kotlinx.coroutines.flow.Flow

interface RadioRepository {
    suspend fun getSongInfo(): Flow<Resource<SongDto>>
}