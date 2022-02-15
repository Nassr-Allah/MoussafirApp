package com.ems.moussafirdima.data.local

import androidx.room.*
import com.ems.moussafirdima.domain.model.MapRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteDao {

    @Query("SELECT * FROM mapRoute ORDER BY duration ASC LIMIT 1")
    fun getRoute(): Flow<MapRoute?>

    @Delete
    suspend fun deleteRoute(mapRoute: MapRoute)

    @Query("SELECT * FROM mapRoute WHERE tripId = :tripId")
    suspend fun getRouteByTripId(tripId: Int): MapRoute?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(mapRoute: MapRoute)

}