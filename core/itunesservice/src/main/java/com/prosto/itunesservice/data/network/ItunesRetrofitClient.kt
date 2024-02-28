package com.prosto.itunesservice.data.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.prosto.itunesservice.data.dto.Response
import com.prosto.itunesservice.data.dto.TracksSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItunesRetrofitClient(
    private val itunesService: ItunesApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET }
        }

        if (dto !is TracksSearchRequest) {
            return Response().apply { resultCode = BAD_GATEWAY }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = itunesService.search(dto.expression)
                response.apply { resultCode = SUCCESS_RESPONSE }
            } catch (e: Throwable) {
                Response().apply { resultCode = REQUEST_ERROR }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
        const val SUCCESS_RESPONSE = 200
        const val NO_INTERNET = -1
        const val REQUEST_ERROR = -2
        const val BAD_GATEWAY = 400
    }
}