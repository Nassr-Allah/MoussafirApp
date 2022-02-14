package com.ems.moussafirdima.domain.use_case.stations

import com.ems.moussafirdima.data.remote.dto.toStation
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.repository.StationsRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val repository: StationsRepository
) {

    operator fun invoke(destination: String): Flow<Resource<List<Station>>> = flow {
        try {
            emit(Resource.Loading<List<Station>>())
            val stations = repository.getAllStations().map { it.toStation() }
                .filter { it.destinations.contains(destination) }.sortedBy { it.distance }
            emit(Resource.Success(stations))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Station>>(e.localizedMessage ?: "An Unexpected Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Station>>("Couldn't reach the server. Check your internet connection"))
        }
    }
}