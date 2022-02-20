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

class GetAllStationsUseCase @Inject constructor(
    private val repository: StationsRepository
) {

    operator fun invoke(): Flow<Resource<List<Station>>> = flow {
        try {
            emit(Resource.Loading<List<Station>>())
            val stations = repository.getAllStations().map { it.toStation() }
            emit(Resource.Success(stations))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Station>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Station>>(e.localizedMessage ?: "Unexpected Error"))

        }
    }

}