package com.prosto.ruskoradio.radioscreen.data.network


import com.prosto.ruskoradio.radioscreen.dto.SongDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getSongTitle(@Url songInfoUrl: String): Response<SongDto>
}