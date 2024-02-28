package com.prosto.itunesservice.domain.api

import com.prosto.itunesservice.domain.models.LoadingTrackStatus
import com.prosto.itunesservice.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface ItunesInteractor {
    suspend fun searchTracks(expression: String): Flow<Pair<List<Track>?, LoadingTrackStatus?>>

    suspend fun getFirstTrack(expression: String): Flow<Pair<Track?, LoadingTrackStatus?>>
}