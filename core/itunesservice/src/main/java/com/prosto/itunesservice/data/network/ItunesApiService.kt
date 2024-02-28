package com.prosto.itunesservice.data.network

import com.prosto.itunesservice.data.dto.TracksSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    suspend fun search(@Query("term") text: String) : TracksSearchResponse
}