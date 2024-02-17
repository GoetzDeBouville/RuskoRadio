package com.prosto.myapplication.radioscreen.domain.radio.impl

import com.hellcorp.restquest.domain.network.models.LoadingStatus
import com.hellcorp.restquest.domain.network.models.Resource
import com.prosto.myapplication.radioscreen.domain.player.api.SongMapper
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioRepository
import com.prosto.myapplication.radioscreen.dto.SongEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RadioInteractorImpl(
    private val repository: RadioRepository,
    private val converter: SongMapper
) : RadioInteractor {
    override suspend fun updateTitle(): Flow<Pair<SongEntity?, LoadingStatus?>> {
        return repository.getSongInfo().map { result ->
            when (result) {
                is Resource.Success -> Pair(converter.map(result.data!!), null)
                is Resource.Error -> Pair(null, result.errorType)
            }
        }
    }
}