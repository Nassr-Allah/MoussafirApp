package com.ems.moussafirdima.data.remote.dto.directions_api

data class Direction(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)