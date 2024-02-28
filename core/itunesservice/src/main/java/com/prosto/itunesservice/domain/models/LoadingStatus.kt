package com.prosto.itunesservice.domain.models

sealed class Resource<T>(val data: T? = null, val errorType: LoadingTrackStatus? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(type: LoadingTrackStatus, data: T? = null) : Resource<T>(data, type)
}

enum class LoadingTrackStatus {
    NO_INTERNET,
    SERVER_ERROR
}
