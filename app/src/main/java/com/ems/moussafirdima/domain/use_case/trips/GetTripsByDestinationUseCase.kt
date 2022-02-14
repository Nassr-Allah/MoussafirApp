package com.ems.moussafirdima.domain.use_case.trips

import androidx.lifecycle.ViewModel
import com.ems.moussafirdima.data.remote.dto.TripDto
import com.ems.moussafirdima.data.remote.dto.toTrip
import com.ems.moussafirdima.domain.model.Trip
import com.ems.moussafirdima.domain.repository.TripsRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTripsByDestinationUseCase @Inject constructor(
    private val repository: TripsRepository
) {

    operator fun invoke(start: String, destination: String): Flow<Resource<List<Trip>>> = flow {
        try {
            emit(Resource.Loading<List<Trip>>())
            val trips = repository.getTrips(start, destination).map { it.toTrip() }
            emit(Resource.Success(trips))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Trip>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Trip>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}