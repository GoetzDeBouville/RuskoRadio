package com.prosto.itunesservice.data.network

import com.prosto.itunesservice.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
