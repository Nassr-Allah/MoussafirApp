package com.ems.moussafirdima.ui.screens.main_app.transportation.bus

import android.location.Location
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.ui.materials.TransportationMethodBus
import com.ems.moussafirdima.ui.navigation.TransportationScreens
import com.ems.moussafirdima.ui.view_models.DirectionsViewModel
import com.ems.moussafirdima.ui.view_models.stations_view_models.StationsListViewModel
import com.ems.moussafirdima.ui.view_models.trips_view_models.TripsListViewModel
import com.ems.moussafirdima.util.*
import com.google.android.libraries.maps.GoogleMap

@Composable
fun BusStationCard(
    navController: NavController,
    viewModel: StationsListViewModel = hiltViewModel(),
    directionsViewModel: DirectionsViewModel = hiltViewModel(),
    destination: String?
) {
    val state = viewModel.state.value
    var searchQuery by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current.applicationContext
    LaunchedEffect(key1 = true, key2 = directionsViewModel.mapRoute) {
        Log.d("BusStationScreen", "Launched Effect")
        directionsViewModel.getDirectionFromDb()
        Log.d("BusStationScreen", "called the method normally")
        if (directionsViewModel.mapRoute.value.encodedPath.isNotEmpty()) {
            placeAvailableStationsMarkers(
                state.stations,
                GlobalVars.map!!,
                context,
                navController,
                destination!!,
                directionsViewModel.mapRoute.value.encodedPath
            )
        }
    }

    Log.d("StationsList", state.toString())
    Card(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .clip(RoundedCornerShape(5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.choose_station),
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Box(modifier = Modifier.fillMaxHeight(0.7f), contentAlignment = Alignment.TopCenter) {
                StatesList(list = state.stations, navController, GlobalVars.map!!, destination)
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TransportationMethodBus()
            }
        }
    }
}

@Composable
fun StatesList(list: List<Station>, navController: NavController, map: GoogleMap, destination: String?) {
    LazyColumn {
        items(list) {
            ProvinceBox(station = it, navController, map, destination)
        }
    }
}

@Composable
fun ProvinceBox(station: Station, navController: NavController, map: GoogleMap, destination: String?) {
    val context = LocalContext.current.applicationContext
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable {
                placeSelectedStationMarker(station, map, context)
                navController.currentBackStackEntry?.arguments?.putParcelable("station", station)
                navController.navigate(
                    TransportationScreens.DetailsCard.route + "/${station.city}" + "/$destination"
                )
            }
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .fillMaxWidth(0.6f)
            .clip(RoundedCornerShape(10))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "${station.name} - ${station.distance} Km",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
    }
}

