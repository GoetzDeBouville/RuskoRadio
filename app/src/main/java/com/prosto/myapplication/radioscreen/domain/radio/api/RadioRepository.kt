package com.prosto.myapplication.radioscreen.domain.radio.api

import com.hellcorp.restquest.domain.network.models.Resource
import com.prosto.myapplication.radioscreen.dto.SongDto
import kotlinx.coroutines.flow.Flow

interface RadioRepository {
    suspend fun getSongInfo(): Flow<Resource<SongDto>>
}