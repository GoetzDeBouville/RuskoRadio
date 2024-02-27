package com.prosto.itunesservice.data.mapper

import com.google.gson.Gson
import com.prosto.itunesservice.data.dto.TrackDto
import com.prosto.itunesservice.domain.models.Track

class TrackMapper {
    fun createJsonFromTracksList(tracks: Array<Track>): String {
        return Gson().toJson(tracks)
    }

    fun createTracksListFromJson(json: String): Array<Track> {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun mapDtoToEntity(dto: TrackDto) = Track(
        trackId = dto.trackId,
        trackName = dto.trackName,
        artistName = dto.artistName,
        trackTimeMillis = dto.trackTimeMillis,
        artworkUrl100 = dto.artworkUrl100,
        collectionName = dto.collectionName,
        releaseDate = dto.releaseDate,
        primaryGenreName = dto.primaryGenreName,
        country = dto.country,
        previewUrl = dto.previewUrl,
    )
}
