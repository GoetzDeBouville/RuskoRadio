package com.prosto.ruskoradio.radioscreen.domain.radio.api

import com.prosto.restquest.domain.network.models.LoadingStatus
import com.prosto.ruskoradio.radioscreen.dto.SongEntity
import kotlinx.coroutines.flow.Flow

interface RadioInteractor {

    suspend fun updateTitle(): Flow<Pair<SongEntity?, LoadingStatus?>>
}