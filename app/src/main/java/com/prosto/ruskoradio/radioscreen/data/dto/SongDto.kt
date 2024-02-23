package com.prosto.ruskoradio.radioscreen.dto

data class SongDto(
    val artist: String,
    val song: String
) {

    override fun toString(): String = "$artist - $song"

}