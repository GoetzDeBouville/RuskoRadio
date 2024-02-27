package com.prosto.itunesservice.data.dto

import com.google.gson.annotations.SerializedName

data class TracksSearchResponse(@SerializedName("results") val tracks: List<TrackDto>) : Response()
