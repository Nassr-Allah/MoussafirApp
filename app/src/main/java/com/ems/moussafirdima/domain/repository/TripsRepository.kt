package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.data.remote.dto.TripDto

interface TripsRepository {

    suspend fun getTrips(start: String, destination: String): List<TripDto>

    suspend fun getAllTrips(): List<TripDto>

}