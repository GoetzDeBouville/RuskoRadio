package com.prosto.ruskoradio.radioscreen.data.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.prosto.ruskoradio.R
import com.prosto.ruskoradio.core.utils.ConfigTool
import com.prosto.ruskoradio.radioscreen.dto.SongDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RetrofitClient(
    private val apiService: ApiService,
    private val context: Context
) {
    suspend fun getSongTitle(): Response<SongDto> {
        ConfigTool.init(context)
        val songInfoUrl = ConfigTool.getAppConfig().songInfoUrl
        if (!isConnected()) {
            return Response.error(
                BAD_GATEWAY_ERROR,
                okhttp3.ResponseBody.create(null, context.getString(R.string.internet_connection_error))
            )
        }
        return withContext(Dispatchers.IO) {
            try {
                apiService.getSongTitle(songInfoUrl)
            } catch (e: Throwable) {
                Response.error(
                    SERVER_ERROR,
                    okhttp3.ResponseBody.create(null, context.getString(R.string.server_error))
                )
            }
        }
    }

    @SuppressLint("NewApi")
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

    companion object {
        const val SERVER_ERROR = 500
        const val BAD_GATEWAY_ERROR = 400
    }
}