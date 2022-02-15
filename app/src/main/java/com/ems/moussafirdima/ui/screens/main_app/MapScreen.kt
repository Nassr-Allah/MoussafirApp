package com.ems.moussafirdima.ui.screens.main_app

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.ui.materials.rememberMapViewWithLifeCycle
import com.ems.moussafirdima.ui.navigation.MapScreens
import com.ems.moussafirdima.ui.navigation.TransportationNavigation
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.DirectionsViewModel
import com.ems.moussafirdima.ui.view_models.stations_view_models.StationsListViewModel
import com.ems.moussafirdima.util.*
import com.google.accompanist.permissions.*
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private val marker = MarkerOptions()
var arrivalTime = ""
var arrivalTimeAlpha = 0f

@ExperimentalPermissionsApi
@Composable
fun MapScreen(
    navController: NavController,
    locationHelper: LastKnownLocationHelper =
        LastKnownLocationHelper(LocalContext.current.applicationContext),
    locationUpdateRequest: LocationUpdateRequest =
        LocationUpdateRequest(LocalContext.current.applicationContext),
    stationsViewModel: StationsListViewModel = hiltViewModel(),
    context: Context = LocalContext.current.applicationContext
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    Log.d("MapScreen", "${locationHelper.location} , ${locationUpdateRequest.location}")
    val stations = stationsViewModel.state.value.stations
    PermissionDialog(permissionState = permissionState)
    Box(modifier = Modifier.fillMaxSize()) {
        MapFun(permissionState, locationHelper, locationUpdateRequest, stations, context)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ) {
            TransportationNavigation()
        }
        Box(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            MapHeader(onItemClick = {
                navController.navigate(MapScreens.AccountScreen.route)
            })
        }
    }
}

@Composable
fun MapHeader(onItemClick: () -> Unit, directionsViewModel: DirectionsViewModel = hiltViewModel()) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    var duration by remember {
        mutableStateOf("")
    }
    var day by remember {
        mutableStateOf("")
    }
    var alpha by remember {
        mutableStateOf(0f)
    }
    val date = "${getCurrentDay()}/${getCurrentMonth()}/${Calendar.getInstance().get(Calendar.YEAR)}"
    val mapRoute = directionsViewModel.mapRoute.value
    val context = LocalContext.current.applicationContext
    LaunchedEffect(key1 = mapRoute) {
        if (mapRoute.arrival.isNotEmpty()) {
            duration = mapRoute.arrival
            day = if (mapRoute.date == date) "today at " else "tomorrow at "
            alpha = 1f
            delay(1000)
            drawPath(GlobalVars.map!!, mapRoute.encodedPath, context)
        } else {
            Log.d("MapRoute", "empty map route")
        }
    }
    LaunchedEffect(key1 = true) {
        directionsViewModel.getDirectionFromDb()
    }
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    backgroundColor = Color.White,
                    disabledLeadingIconColor = Orange,
                    leadingIconColor = Orange
                ),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_location_pin),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    TrailingIcon(onItemClick = { onItemClick() })
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (duration.isNotEmpty()) {
                    Log.d("MapScreen","$date and route date is ${mapRoute.date}")
                    Text(
                        text = "Arrival: $day $duration",
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(R.dimen.body1).value.sp,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15))
                            .background(Color.Black)
                            .padding(5.dp)
                            .alpha(alpha)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                LocationButton()
            }
        }
    }
}

@Composable
fun TrailingIcon(onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .clip(CircleShape)
            .background(Color.Black)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.setting_lines),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onItemClick()
                }
        )
    }
}

@Composable
fun LocationButton() {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .clip(CircleShape)
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .padding(0.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .clickable {
                    selfLocate(GlobalVars.map!!)
                }
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_my_location),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun MapFun(
        permissionState: MultiplePermissionsState,
        locationHelper: LastKnownLocationHelper,
        locationUpdateRequest: LocationUpdateRequest,
        stations: List<Station>,
        context: Context,
) {
    val mapView = rememberMapViewWithLifeCycle()
    var isReady by remember {
        mutableStateOf(false)
    }
    val lastLocationState = locationHelper.location.value
    val locationUpdateState = locationUpdateRequest.location.value
    LaunchedEffect(key1 = lastLocationState.isLoading, key2 = locationUpdateState.isLoading) {
        if (!lastLocationState.isLoading && lastLocationState.location != null) {
            isReady = true
        } else if (!lastLocationState.isLoading && lastLocationState.location == null) {
            if (!locationUpdateState.isLoading && locationUpdateState.location != null) {
                isReady = true
            } else if (!locationUpdateState.isLoading && locationUpdateState.location == null) {
                Toast.makeText(context, "Failed to get your location", Toast.LENGTH_SHORT).show()
            }
        }
    }
    if (permissionState.allPermissionsGranted) {
        AndroidView(factory = { mapView }) {
            CoroutineScope(Dispatchers.Main).launch {
                GlobalVars.map = it.awaitMap()
                val latestLocation = locationHelper.location.value
                delay(1000)
                if (isReady) {
                    isReady = false
                    if (latestLocation.location != null) {
                        placeLocationMarker(
                            latestLocation.location,
                            GlobalVars.map!!,
                            context,
                            marker
                        )
                        placeStationsMarkers(
                            stations,
                            latestLocation.location,
                            GlobalVars.map!!,
                            context
                        )
                    } else {
                        val location = locationUpdateRequest.location.value
                        if (location.location != null) {
                            placeLocationMarker(
                                location.location,
                                GlobalVars.map!!,
                                context,
                                marker
                            )
                            placeStationsMarkers(
                                stations,
                                location.location,
                                GlobalVars.map!!,
                                context
                            )
                        }
                    }
                }
            }
        }
    } else {
        Toast.makeText(context, "Enable Location Permission in Settings", Toast.LENGTH_SHORT).show()
    }
}

@ExperimentalPermissionsApi
@Composable
fun PermissionDialog(permissionState: MultiplePermissionsState) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 =lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    permissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

