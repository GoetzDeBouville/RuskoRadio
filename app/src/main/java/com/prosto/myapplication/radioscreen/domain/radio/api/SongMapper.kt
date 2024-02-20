package com.prosto.myapplication.radioscreen.domain.radio.api

import com.prosto.myapplication.radioscreen.dto.SongDto
import com.prosto.myapplication.radioscreen.dto.SongEntity

interface SongMapper {
    fun map(song: SongDto): SongEntity
}