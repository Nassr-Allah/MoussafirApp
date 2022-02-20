package com.ems.moussafirdima.ui.screens.main_app.transportation.bus

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.model.Trip
import com.ems.moussafirdima.ui.materials.TransportationMethodBus
import com.ems.moussafirdima.ui.navigation.TransportationScreens
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.trips_view_models.TripsListViewModel

@ExperimentalComposeUiApi
@Composable
fun BusDestinationCard(
    navController: NavController,
    tripsListViewModel: TripsListViewModel = hiltViewModel()
) {
    val state = tripsListViewModel.state.value
    var searchQuery by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Log.d("BusDestinationCard", "${tripsListViewModel.filteredTrips.value}")
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        if (searchQuery.isEmpty()) {
                            tripsListViewModel.restoreList()
                            keyboardController?.hide()
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        backgroundColor = Color.Transparent,
                        disabledLeadingIconColor = Color.Gray,
                        leadingIconColor = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth(0.6f),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                )
                Button(
                    onClick = {
                        tripsListViewModel.filterTrips(searchQuery)
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(15)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(
                        text = stringResource(id = R.string.search),
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                        color = Color.White
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.choose_a_destination),
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Box(
                modifier = Modifier.fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                if (!state.isLoading && state.error.isEmpty()) {
                    StationsList(tripsListViewModel.filteredTrips.value, navController)
                } else if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Orange)
                    }
                } else if (!state.isLoading && state.error.isNotEmpty()) {
                    Text(
                        text = "Error: ${state.error}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                }
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
fun StationsList(trips: List<Trip>?, navController: NavController) {
    var destinations = mutableListOf<String>()
    for (trip in trips!!) {
        destinations.add(trip.destination)
    }
    if (destinations.isNotEmpty()) {
        destinations = destinations.distinct() as MutableList<String>
    }
    LazyColumn {
        items(destinations) { destination ->
            ProvinceStationBox(destination, navController)
        }
    }
}

@Composable
fun ProvinceStationBox(destination: String, navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable {
                navController.navigate(TransportationScreens.BusStationCard.route + "/$destination")
            }
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .fillMaxWidth(0.6f)
            .clip(RoundedCornerShape(10))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = destination,
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
    }
}
