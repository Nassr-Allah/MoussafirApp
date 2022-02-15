package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.local.RouteDao
import com.ems.moussafirdima.domain.model.MapRoute
import com.ems.moussafirdima.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RouteRepositoryImpl (private val dao: RouteDao) : RouteRepository {

    override fun getRoute(): Flow<MapRoute?> {
        return dao.getRoute()
    }

    override suspend fun insertRoute(mapRoute: MapRoute) {
        dao.insertRoute(mapRoute)
    }

    override suspend fun deleteRoute(mapRoute: MapRoute) {
        dao.deleteRoute(mapRoute)
    }

    override suspend fun getRouteByTripId(tripId: Int): MapRoute? {
        return dao.getRouteByTripId(tripId)
    }
}