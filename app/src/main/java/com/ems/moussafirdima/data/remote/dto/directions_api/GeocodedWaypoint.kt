package com.ems.moussafirdima.data.remote.dto.directions_api

data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)