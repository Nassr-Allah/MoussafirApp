package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.domain.model.MapRoute
import kotlinx.coroutines.flow.Flow

interface RouteRepository {

    fun getRoute(): Flow<MapRoute?>

    suspend fun insertRoute(mapRoute: MapRoute)

    suspend fun deleteRoute(mapRoute: MapRoute)

    suspend fun getRouteByTripId(tripId: Int): MapRoute?

}