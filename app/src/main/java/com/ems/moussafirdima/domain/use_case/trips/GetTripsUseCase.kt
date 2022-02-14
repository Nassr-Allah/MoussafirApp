package com.ems.moussafirdima.domain.use_case.trips

import com.ems.moussafirdima.data.remote.dto.toTrip
import com.ems.moussafirdima.domain.model.Trip
import com.ems.moussafirdima.domain.repository.TripsRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTripsUseCase @Inject constructor(
    private val repository: TripsRepository
) {

    operator fun invoke(): Flow<Resource<List<Trip>>> = flow {
        try {
            emit(Resource.Loading<List<Trip>>())
            val trips = repository.getAllTrips().map { it.toTrip() }
            emit(Resource.Success(trips))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Trip>>(e.localizedMessage ?: "An Unexpected Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Trip>>("Couldn't reach the server. Check your internet connection"))
        }
    }
}