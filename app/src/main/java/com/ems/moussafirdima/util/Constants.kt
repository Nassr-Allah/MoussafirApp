package com.ems.moussafirdima.util

import android.Manifest
import com.google.android.libraries.maps.GoogleMap

object Constants {
    const val BASE_URL = "https://moussafirdima.herokuapp.com/"
    const val DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/"
    const val PARAM_TOKEN = "token"
    const val PARAM_START = "start"
    const val PARAM_DESTINATION = "destination"
    const val PARAM_PHONE = "phone"
    const val PARAM_TICKET = "ticket"
    const val DEFAULT_ZOOM = 11f
    const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
}

object GlobalVars {
    var map: GoogleMap? = null
}