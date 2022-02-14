package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.remote.dto.TripDto
import com.ems.moussafirdima.domain.repository.TripsRepository
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: MoussafirApi
) : TripsRepository {

    override suspend fun getTrips(start: String, destination: String): List<TripDto> {
        return api.getTrips(start, destination)
    }

    override suspend fun getAllTrips(): List<TripDto> {
        return api.getAllTrips()
    }
}