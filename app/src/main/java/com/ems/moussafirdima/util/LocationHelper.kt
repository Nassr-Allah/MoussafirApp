package com.ems.moussafirdima.util

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.data.remote.dto.directions_api.Direction
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.ui.navigation.TransportationScreens
import com.ems.moussafirdima.ui.view_models.states.LocationState
import com.google.android.libraries.maps.model.*
import com.google.maps.android.PolyUtil

var currentLocation: Location? = null

class LastKnownLocationHelper(val context: Context) {

    private val _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location

    init {
        _location.value = LocationState(isLoading = true)
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            if (ContextCompat.checkSelfPermission(context, Constants.FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(context, Constants.COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.lastLocation.apply {
                        addOnSuccessListener {
                            _location.value = LocationState(location = it)
                            currentLocation = it
                            Log.d("Location Helper", "Location granted")
                            Log.d("Location Helper", location.toString())
                        }
                        addOnFailureListener {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            Log.d("Location Helper", it.message ?: "Unexpected Error in fused provider")
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Enable location permission in Settings", Toast.LENGTH_SHORT).show()
            }

        } catch (e: SecurityException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            Log.d("Location Helper", e.message ?: "Unexpected Security Error")
        }
    }

}

class LocationUpdateRequest(val context: Context) {

    private val _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result ?: return
            currentLocation = result.locations[0]
            _location.value = LocationState(isLoading = false, location = result.locations[0])
        }
    }
    private val locationRequest = LocationRequest.create()?.apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    init {
        try {
            _location.value = LocationState(isLoading = true)
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            Log.d("Location Update", e.message ?: "Unexpected error in location update")
        }

    }

}

fun placeLocationMarker(location: Location, map: GoogleMap, context: Context, markerOptions: MarkerOptions) {
    val latLng = LatLng(location.latitude, location.longitude)
    markerOptions.apply {
        title("me")
        position(latLng)
        icon(bitmapFromVector(context, R.drawable.ic_my_location))
    }
    map.addMarker(markerOptions)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constants.DEFAULT_ZOOM))
}

fun drawRoute(map: GoogleMap, direction: Direction) {
    val list = mutableListOf<LatLng>()
    for (route in direction.routes) {
        for (leg in route.legs) {
            for (step in leg.steps) {
                val path = PolyUtil.decode(step.polyline.points)
                for (point in path) {
                    val latLng = LatLng(point.latitude, point.longitude)
                    list.add(latLng)
                }
            }
        }
    }
    val options = PolylineOptions().apply {
        addAll(list)
    }
    map.addPolyline(options)
    Log.d("Polyline", list.toString())
    Log.d("Polyline", options.points.toString())
}

fun drawPath(map: GoogleMap, path: String, context: Context) {
    map.clear()
    val marker = MarkerOptions()
    placeLocationMarker(currentLocation!!, map, context, marker)
    val decodedPath = PolyUtil.decode(path)
    val list = mutableListOf<LatLng>()
    for (point in decodedPath) {
        val latLng = LatLng(point.latitude, point.longitude)
        list.add(latLng)
    }
    val polyline = PolylineOptions().apply {
        addAll(list)
    }
    map.addPolyline(polyline)
}

fun placeStationsMarkers(stations: List<Station>, currentLocation: Location, map: GoogleMap, context: Context) {
    stations.forEach { station ->
        val location = Location("").apply {
            latitude = station.lat!!.toDouble()
            longitude = station.lng!!.toDouble()
        }
        if (isDistanceLessThanMax(currentLocation, location)) {
            val markerOptions = MarkerOptions().apply {
                title(station.name)
                position(LatLng(location.latitude, location.longitude))
                icon(bitmapFromVector(context, R.drawable.ic_bus_stop))
            }
            map.addMarker(markerOptions)
        }
    }
}

fun placeSelectedStationMarker(station: Station, map: GoogleMap, context: Context) {
    val latLng = LatLng(station.lat!!.toDouble(), station.lng!!.toDouble())
    val marker = MarkerOptions().apply {
        title(station.name)
        position(latLng)
        icon(bitmapFromVector(context, R.drawable.ic_bus_stop))
    }
    map.addMarker(marker)
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9f))
}

fun selfLocate(map: GoogleMap) {
    val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constants.DEFAULT_ZOOM))
}

fun placeAvailableStationsMarkers(
    stations: List<Station>,
    map: GoogleMap,
    context: Context,
    navController: NavController,
    destination: String,
    path: String
) {
    map.clear()
    drawPath(map, path, context)
    val currentMarker = MarkerOptions().apply {
        title("me")
        position(LatLng(currentLocation!!.latitude, currentLocation!!.longitude))
        icon(bitmapFromVector(context, R.drawable.ic_my_location))
    }
    placeLocationMarker(currentLocation!!, map, context, currentMarker)
    for (station in stations) {
        val latLng = LatLng(station.lat!!.toDouble(), station.lng!!.toDouble())
        val location = Location("").apply {
            latitude = station.lat.toDouble()
            longitude = station.lng.toDouble()
        }
        if (isDistanceLessThanMax(currentLocation!!, location)) {
            val marker = MarkerOptions().apply {
                title(station.name)
                position(latLng)
                icon(bitmapFromVector(context, R.drawable.ic_bus_stop))
            }
            map.addMarker(marker)
            handleMarkerClick(map, navController, destination, station)
        }
    }
}

private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun isDistanceLessThanMax(start: Location, end: Location) : Boolean {
    val distance = start.distanceTo(end) / 1000
    return distance <= 30
}

fun calculateDistance(start: Location?, end: Location) : Int {
    return if (start != null) {
        (start.distanceTo(end) / 1000).toInt()
    } else {
        0
    }
}

fun handleMarkerClick(map: GoogleMap, navController: NavController, destination: String, station: Station) {
    map.setOnMarkerClickListener {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 12f))
        navController.navigate(TransportationScreens.DetailsCard.route + "/${station.city}" + "/$destination") {
            popUpTo(TransportationScreens.BusDestinationCard.route)
        }
        true
    }
}

fun moveCamera(map: GoogleMap, latLng: LatLng) {
    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
}