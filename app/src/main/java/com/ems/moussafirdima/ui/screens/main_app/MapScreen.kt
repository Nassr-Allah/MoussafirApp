package com.ems.moussafirdima.ui.screens.main_app

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
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
import com.ems.moussafirdima.ui.view_models.stations_view_models.AllStationsViewModel
import com.ems.moussafirdima.ui.view_models.stations_view_models.StationsListViewModel
import com.ems.moussafirdima.util.*
import com.google.accompanist.permissions.*
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private val marker = MarkerOptions()

@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@Composable
fun MapScreen(
    navController: NavController,
    locationHelper: LastKnownLocationHelper =
        LastKnownLocationHelper(LocalContext.current.applicationContext),
    locationUpdateRequest: LocationUpdateRequest =
        LocationUpdateRequest(LocalContext.current.applicationContext),
    stationsViewModel: AllStationsViewModel = hiltViewModel(),
    context: Context = LocalContext.current.applicationContext
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val stations = stationsViewModel.state.value
    Log.d("MapScreen", "${locationHelper.location} , ${locationUpdateRequest.location}")
    Log.d("MapScreen", "${stationsViewModel.filteredStations.value}")
    Log.d("MapScreen", "$stations")
    PermissionDialog(permissionState = permissionState)
    Box(modifier = Modifier.fillMaxSize()) {
        MapFun(permissionState, locationHelper, locationUpdateRequest, stations.stations, context)
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
            MapHeader(
                onItemClick = {
                    navController.navigate(MapScreens.AccountScreen.route)
                },
                stations = stationsViewModel.filteredStations.value,
                stationsViewModel = stationsViewModel
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MapHeader(
    onItemClick: () -> Unit,
    directionsViewModel: DirectionsViewModel = hiltViewModel(),
    stationsViewModel: AllStationsViewModel,
    stations: List<Station>,
) {
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
    var minHeight by remember {
        mutableStateOf(0.dp)
    }
    var maxHeight by remember {
        mutableStateOf(0.dp)
    }
    val date =
        "${getCurrentDay()}/${getCurrentMonth()}/${Calendar.getInstance().get(Calendar.YEAR)}"
    val mapRoute = directionsViewModel.mapRoute.value
    val context = LocalContext.current.applicationContext
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = mapRoute) {
        if (mapRoute.arrival.isNotEmpty()) {
            duration = mapRoute.arrival
            day = if (mapRoute.date == date) "${context.getString(R.string.today_at)} "
            else "${context.getString(R.string.tomorrow_at)} "
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
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    stationsViewModel.filterStation(searchQuery)
                    minHeight = if (searchQuery.isEmpty()) 0.dp else 50.dp
                    maxHeight = if (searchQuery.isEmpty()) 0.dp else 400.dp
                },
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
                },
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                if (duration.isNotEmpty()) {
                    Log.d("MapScreen", "$date and route date is ${mapRoute.date}")
                    Text(
                        text = "${stringResource(id = R.string.arrival)}: $day $duration",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(minHeight, maxHeight)
                    .padding(horizontal = 20.dp)
                    .background(Color.White),
                contentPadding = PaddingValues(20.dp),
            ) {
                items(stations) { station ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable {
                                placeSelectedStationMarker(station, GlobalVars.map!!, context)
                                minHeight = 0.dp
                                maxHeight = 0.dp
                                keyboardController?.hide()
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = station.name,
                            style = MaterialTheme.typography.body1,
                            color = Color.Black,
                            fontSize = dimensionResource(R.dimen.body1).value.sp
                        )
                    }
                }
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
    var isPlaced by remember {
        mutableStateOf(false)
    }
    val lastLocationState = locationHelper.location.value
    val locationUpdateState = locationUpdateRequest.location.value
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = lastLocationState.isLoading, key2 = locationUpdateState.isLoading) {
        if (!lastLocationState.isLoading && lastLocationState.location != null) {
            isReady = true
        } else if (!lastLocationState.isLoading && lastLocationState.location == null) {
            if (!locationUpdateState.isLoading && locationUpdateState.location != null) {
                isReady = true
            } else if (!locationUpdateState.isLoading && locationUpdateState.location == null) {
                Toast.makeText(context, context.getString(R.string.failed_to_get_your_location), Toast.LENGTH_SHORT).show()
            }
        }
    }
    AndroidView(factory = { mapView }) {
        scope.launch {
            GlobalVars.map = it.awaitMap()
            val latestLocation = locationHelper.location.value
            delay(1000)
            if (isReady) {
                isReady = false
                if (permissionState.allPermissionsGranted) {
                    if (latestLocation.location != null) {
                        if (!isPlaced) {
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
                            isPlaced = true
                        }
                    } else {
                        val location = locationUpdateRequest.location.value
                        if (location.location != null) {
                            if (!isPlaced) {
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
                                isPlaced = true
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.enable_location_permission), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun PermissionDialog(permissionState: MultiplePermissionsState) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
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

