package com.prosto.itunesservice.domain.api

import com.prosto.itunesservice.domain.models.LoadingTrackStatus
import com.prosto.itunesservice.domain.models.Resource
import com.prosto.itunesservice.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface ItunesRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>

    suspend fun getFirstTrack(expression: String): Flow<Resource<Track>>
}
