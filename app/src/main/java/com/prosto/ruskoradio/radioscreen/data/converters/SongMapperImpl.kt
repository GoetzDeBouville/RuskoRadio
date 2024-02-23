package com.prosto.ruskoradio.radioscreen.data.converters

import com.prosto.ruskoradio.radioscreen.domain.radio.api.SongMapper
import com.prosto.ruskoradio.radioscreen.dto.SongDto
import com.prosto.ruskoradio.radioscreen.dto.SongEntity

class SongMapperImpl : SongMapper {
    override fun map(song: SongDto) = SongEntity (
        song.toString()
    )
}