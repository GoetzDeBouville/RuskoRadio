package com.prosto.itunesservice.data

import com.prosto.itunesservice.data.dto.TracksSearchRequest
import com.prosto.itunesservice.data.dto.TracksSearchResponse
import com.prosto.itunesservice.data.mapper.TrackMapper
import com.prosto.itunesservice.data.network.ItunesRetrofitClient
import com.prosto.itunesservice.domain.api.ItunesRepository
import com.prosto.itunesservice.domain.models.LoadingTrackStatus
import com.prosto.itunesservice.domain.models.Resource
import com.prosto.itunesservice.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItunesRepositoryImpl(
    private val networkClient: ItunesRetrofitClient,
) : ItunesRepository {
    private val mapper = TrackMapper()
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        when (response.resultCode) {
            ItunesRetrofitClient.NO_INTERNET -> emit(Resource.Error(LoadingTrackStatus.NO_INTERNET))

            ItunesRetrofitClient.SUCCESS_RESPONSE -> {
                emit(Resource.Success((response as TracksSearchResponse).tracks.map {
                    mapper.mapDtoToEntity(it)
                }))
            }

            else -> emit(Resource.Error(LoadingTrackStatus.SERVER_ERROR))
        }
    }

    override suspend fun getFirstTrack(expression: String): Flow<Resource<Track>> = flow {
        searchTracks(expression).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val firstTrack = result.data?.firstOrNull()
                    if (firstTrack != null) {
                        emit(Resource.Success(firstTrack))
                    } else {
                        emit(Resource.Error(LoadingTrackStatus.SERVER_ERROR))
                    }
                }

                is Resource.Error -> {
                    emit(Resource.Error(LoadingTrackStatus.SERVER_ERROR))
                }
            }
        }
    }
}