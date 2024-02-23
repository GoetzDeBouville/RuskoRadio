package com.prosto.ruskoradio.radioscreen.domain.radio.api

import com.prosto.ruskoradio.radioscreen.dto.SongDto
import com.prosto.ruskoradio.radioscreen.dto.SongEntity

interface SongMapper {
    fun map(song: SongDto): SongEntity
}