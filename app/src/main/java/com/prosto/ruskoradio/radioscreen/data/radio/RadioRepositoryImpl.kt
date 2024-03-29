package com.prosto.ruskoradio.radioscreen.data.radio

import com.prosto.restquest.domain.network.models.LoadingStatus
import com.prosto.restquest.domain.network.models.Resource
import com.prosto.ruskoradio.radioscreen.data.network.RetrofitClient
import com.prosto.ruskoradio.radioscreen.domain.radio.api.RadioRepository
import com.prosto.ruskoradio.radioscreen.dto.SongDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RadioRepositoryImpl(
    private val networkCLient: RetrofitClient
) : RadioRepository {
    override suspend fun getSongInfo(): Flow<Resource<SongDto>> = flow {
        try {
            val response = networkCLient.getSongTitle()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: this
            } else {
                emit(Resource.Error(LoadingStatus.SERVER_ERROR))
            }
        } catch (e: IOException) {
            emit(Resource.Error(LoadingStatus.NO_INTERNET))
        } catch (e: HttpException) {
            emit(Resource.Error(LoadingStatus.SERVER_ERROR))
        } catch (e: Exception) {
            emit(Resource.Error(LoadingStatus.SERVER_ERROR))
        }
    }
}