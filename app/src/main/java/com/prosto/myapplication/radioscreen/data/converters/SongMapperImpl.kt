package com.prosto.myapplication.radioscreen.data.converters

import com.prosto.myapplication.radioscreen.domain.player.api.SongMapper
import com.prosto.myapplication.radioscreen.dto.SongDto
import com.prosto.myapplication.radioscreen.dto.SongEntity

class SongMapperImpl : SongMapper {
    override fun map(song: SongDto) = SongEntity (
        song.toString()
    )
}