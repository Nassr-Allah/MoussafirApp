package com.ems.moussafirdima.data.remote

import com.ems.moussafirdima.data.remote.dto.directions_api.Direction
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {

    @GET("json?")
    suspend fun getDirection(
        @Query("origin", encoded = true) origin: String,
        @Query("destination", encoded = true) destination: String,
        @Query("departure_time", encoded = true) departureTime: Long,
        @Query("key", encoded = true) key: String
    ): Direction


}