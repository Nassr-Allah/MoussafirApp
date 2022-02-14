package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.data.remote.dto.StationDto

interface StationsRepository {

    suspend fun getAllStations(): List<StationDto>

}