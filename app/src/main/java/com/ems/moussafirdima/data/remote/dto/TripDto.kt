package com.ems.moussafirdima.data.remote.dto

import com.ems.moussafirdima.domain.model.Trip
import com.google.gson.annotations.SerializedName
import java.sql.Time

data class TripDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("start")
    val start: String = "",
    @SerializedName("destination")
    val destination: String = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("time")
    val time: String = "",
    @SerializedName("company")
    val company: CompanyDto? = null,
    @SerializedName("num_of_places")
    val numOfPlaces: Int = 0,
    @SerializedName("driver")
    val driver: String = ""
)

fun TripDto.toTrip(): Trip {
    return Trip(
        id,
        start,
        destination,
        price,
        status,
        time,
        numOfPlaces,
        driver
    )
}
