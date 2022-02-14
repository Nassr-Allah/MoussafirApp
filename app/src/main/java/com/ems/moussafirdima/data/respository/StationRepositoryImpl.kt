package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.remote.dto.StationDto
import com.ems.moussafirdima.domain.repository.StationsRepository
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val api: MoussafirApi
) : StationsRepository {

    override suspend fun getAllStations(): List<StationDto> {
        return api.getAllStations()
    }

}