package com.ems.moussafirdima.data.remote.dto

import android.location.Location
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.util.calculateDistance
import com.ems.moussafirdima.util.currentLocation
import com.google.gson.annotations.SerializedName

data class StationDto(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("destinations")
    val destinations: List<String> = mutableListOf(),
    @SerializedName("city")
    val city: String = "",
    @SerializedName("latitude")
    val lat: Float? = null,
    @SerializedName("longitude")
    val lng: Float? = null
)

fun StationDto.toStation(): Station {
    val location = Location("").apply {
        latitude = lat!!.toDouble()
        longitude = lng!!.toDouble()
    }
    val distance = calculateDistance(currentLocation!!, location)
    return Station(
        name,
        destinations,
        city,
        lat,
        lng,
        distance
    )
}
