package com.prosto.itunesservice.domain.impl

import com.prosto.itunesservice.domain.api.ItunesInteractor
import com.prosto.itunesservice.domain.api.ItunesRepository
import com.prosto.itunesservice.domain.models.LoadingTrackStatus
import com.prosto.itunesservice.domain.models.Resource
import com.prosto.itunesservice.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItunesInteractorImpl(
    private val repository: ItunesRepository
) : ItunesInteractor {
    override suspend fun searchTracks(expression: String): Flow<Pair<List<Track>?, LoadingTrackStatus?>> {
        return repository.searchTracks(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.errorType)
            }
        }
    }

    override suspend fun getFirstTrack(expression: String): Flow<Pair<Track?, LoadingTrackStatus?>> {
        return repository.getFirstTrack(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.errorType)
            }
        }
    }
}