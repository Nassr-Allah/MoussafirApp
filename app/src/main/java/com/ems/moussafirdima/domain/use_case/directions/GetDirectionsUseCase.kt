package com.ems.moussafirdima.domain.use_case.directions

import android.util.Log
import com.ems.moussafirdima.data.remote.DirectionsApi
import com.ems.moussafirdima.data.remote.dto.directions_api.Direction
import com.ems.moussafirdima.domain.model.MapRoute
import com.ems.moussafirdima.domain.model.Trip
import com.ems.moussafirdima.domain.repository.RouteRepository
import com.ems.moussafirdima.util.Resource
import com.ems.moussafirdima.util.getCurrentDay
import com.ems.moussafirdima.util.getCurrentMonth
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class GetDirectionsUseCase @Inject constructor(
    private val api: DirectionsApi,
    private val repository: RouteRepository
) {

    operator fun invoke(
        origin: String,
        direction: String,
        key: String,
        date: String,
        trip: Trip,
    ):
            Flow<Resource<Direction>> = flow {
        Log.d("DirectionUseCase", "called")
        try {
            val tripHourInSeconds = "${trip.time[0]}${trip.time[1]}".toInt() * 3600
            val tripMinuteInSeconds = "${trip.time[3]}${trip.time[4]}".toInt() * 60
            val departureTime = ((Calendar.getInstance(Locale.FRANCE).timeInMillis) / 60) +
                    tripHourInSeconds + tripMinuteInSeconds
            Log.d("DirectionUseCase", "loading")
            emit(Resource.Loading<Direction>())
            val result = api.getDirection(origin, direction, departureTime, key)
            Log.d("DirectionUseCase", result.toString())
            val list = mutableListOf<LatLng>()
            for (route in result.routes) {
                for (leg in route.legs) {
                    for (step in leg.steps) {
                        val points = step.polyline.points
                        val path = PolyUtil.decode(points)
                        for (point in path) {
                            val latLng = LatLng(point.latitude, point.longitude)
                            list.add(latLng)
                        }
                    }
                }
            }
            val encodedPath = PolyUtil.encode(list)
            val duration = (result.routes[0].legs[0].duration.value) * 1000
            val durationToArrival =
                Calendar.getInstance(TimeZone.getDefault()).timeInMillis + duration
            val hour = "${((durationToArrival / (1000 * 60 * 60)) % 24) + 1}"
            val minute = if ((durationToArrival / (1000 * 60)) % 60 >= 10) {
                "${(durationToArrival / (1000 * 60)) % 60}"
            } else {
                "0${(durationToArrival / (1000 * 60)) % 60}"
            }
            val arrival = "$hour:$minute"
            val mapRoute = MapRoute(
                encodedPath = encodedPath,
                duration = durationToArrival,
                arrival = arrival,
                date = date,
                tripId = trip.id
            )
            Log.d("DirectionUseCase", mapRoute.toString())
            repository.insertRoute(mapRoute)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error<Direction>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Direction>(e.localizedMessage ?: "Unxpected Error"))
        }
    }

}