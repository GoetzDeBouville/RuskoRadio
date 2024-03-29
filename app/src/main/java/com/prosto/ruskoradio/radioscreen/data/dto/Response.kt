package com.prosto.ruskoradio.radioscreen.data.dto

sealed class Response {
    data class Error(val errorMessage: String) : Response()
    data class SongInfo(val title: String) : Response()
}