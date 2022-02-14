package com.ems.moussafirdima.ui.screens.main_app.transportation.bus

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.domain.model.Trip
import com.ems.moussafirdima.ui.materials.CustomSnackBar
import com.ems.moussafirdima.ui.materials.TransportationMethodBus
import com.ems.moussafirdima.ui.navigation.TransportationScreens
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.DirectionsViewModel
import com.ems.moussafirdima.ui.view_models.ticket_view_models.TicketPostViewModel
import com.ems.moussafirdima.ui.view_models.trips_view_models.FilteredTripsViewModel
import com.ems.moussafirdima.ui.view_models.trips_view_models.TripsListViewModel
import com.ems.moussafirdima.util.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.util.*

var time: String = ""
var day = getCurrentDay()
var month = getCurrentMonth()

@Composable
fun DetailsCard(
    filteredTripsViewModel: FilteredTripsViewModel = hiltViewModel(),
    ticketPostViewModel: TicketPostViewModel = hiltViewModel(),
    directionsViewModel: DirectionsViewModel = hiltViewModel(),
    navController: NavController,
    station: Station?
) {
    val context = LocalContext.current.applicationContext
    var tripTime by remember {
        mutableStateOf(time)
    }
    var selectedDay by remember {
        mutableStateOf(getCurrentDay())
    }
    var selectedMonth by remember {
        mutableStateOf(getCurrentMonth())
    }
    val state = filteredTripsViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    Log.d("TripsList", state.trips.toString())
    if (!state.isLoading && state.trips.isNotEmpty()) {
        val todayTimes = mutableListOf<String>()
        val times = mutableListOf<String>()
        state.trips.forEach { trip ->
            if (trip.time.removeRange(5, 8) > getCurrentTime()) {
                todayTimes.add(trip.time.removeRange(5, 8))
            }
            times.add(trip.time.removeRange(5, 8))
        }
        var timesList by remember {
            mutableStateOf(todayTimes)
        }
        Log.d("DateTime", "${getCurrentTime()} ${getCurrentDate(time)}")
        Card(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(5))
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    CustomSnackBar(snackbarHostState = it) {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    DetailsCardHeader(selectedDay, selectedMonth) { date ->
                        if (
                            date.dayOfMonth == LocalDate.now().dayOfMonth &&
                            date.monthValue == LocalDate.now().monthValue
                        ) {
                            day = formatDate(date.dayOfMonth)
                            month = formatDate(date.monthValue)
                            selectedDay = day
                            selectedMonth = month
                            timesList = todayTimes
                        } else if (date.isBefore(LocalDate.now())) {
                            Toast.makeText(
                                context,
                                "This date has already passed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            day = formatDate(date.dayOfMonth)
                            month = formatDate(date.monthValue)
                            selectedDay = day
                            selectedMonth = month
                            timesList = times

                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        if (timesList.isEmpty()) {
                            Text(text = "No Trips Available Today")
                        } else {
                            TimeChips(list = timesList) {
                                tripTime = time
                            }
                        }
                    }
                    BusTripDetails(
                        tripTime,
                        state.trips[0],
                        ticketPostViewModel,
                        directionsViewModel,
                        navController,
                        station,
                        scaffoldState
                    )
                    TransportationMethodBus()
                }
            }
        }
    } else if (!state.isLoading && state.trips.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(5))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No trips available for now",
                    style = MaterialTheme.typography.h2,
                    fontSize = dimensionResource(R.dimen.h2).value.sp,
                    color = Color.Black
                )
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(5))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Orange)
            }
        }
    }
}

@Composable
fun DetailsCardHeader(selectedDay: String, selectedMonth: String, onDatePicked: (date: LocalDate) -> Unit) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("CANCEL")
        },
    ) {
        datepicker { date ->
            onDatePicked(date)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Book your ticket",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Orange, shape = RoundedCornerShape(10.dp))
                .clickable {
                    dialogState.show()
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$selectedDay/$selectedMonth",
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp
            )
        }
    }
}

@Composable
fun TimeChips(list: List<String>, onItemClick: () -> Unit) {
    var currentItem by remember {
        mutableStateOf(100)
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        items(list.size) {
            TimeBox(time = list[it], isSelected = it == currentItem) {
                currentItem = it
                time = list[it]
                Log.d("TimeTest", time)
                onItemClick()
            }
        }
    }
}

@Composable
fun TimeBox(isSelected: Boolean = false, time: String, onItemClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        if (isSelected) Orange else Color.Transparent
    )
    val textColor by animateColorAsState(
        if (isSelected) Color.White else Color.Gray
    )
    val borderColor by animateColorAsState(
        if (isSelected) Color.Transparent else Color.Gray
    )
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(15))
            .background(backgroundColor)
            .clickable { onItemClick() }
            .padding(horizontal = 15.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.body1,
            color = textColor,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
    }
}

@Composable
fun BusTripDetails(
    tripTime: String,
    trip: Trip,
    ticketPostViewModel: TicketPostViewModel,
    directionsViewModel: DirectionsViewModel,
    navController: NavController,
    station: Station?,
    scaffoldState: ScaffoldState
) {

    var tickets by remember {
        mutableStateOf(1)
    }
    var price by remember {
        mutableStateOf(trip.price)
    }
    val context = LocalContext.current.applicationContext
    val state = ticketPostViewModel.state.value
    val directionsState = directionsViewModel.state.value
    LaunchedEffect(key1 = state) {
        if (!state.isLoading && state.response != null && state.response.isSuccessful) {
            val origin = "${currentLocation?.latitude},${currentLocation?.longitude}"
            val destination = "${station?.lat},${station?.lng}"
            val key = context.getString(R.string.google_api_key)
            val date = "$day/$month/${Calendar.getInstance().get(Calendar.YEAR)}"
            directionsViewModel.getDirection(origin, destination, key, date)
        }
        if (state.error == "Date Error") {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Reservation Limit is 24 hours",
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Indefinite
            )
        }
    }
    LaunchedEffect(key1 = directionsState) {
        if (!directionsState.isLoading && directionsState.direction != null) {
            directionsViewModel.getDirectionFromDb()
            navController.navigate(TransportationScreens.TicketCard.route) {
                popUpTo(TransportationScreens.BusDestinationCard.route)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
            .padding(horizontal = 5.dp, vertical = 15.dp)
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Orange)
            }
        } else if (!state.isLoading) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = tripTime,
                        style = MaterialTheme.typography.h2,
                        color = Orange,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                    Text(
                        text = "$price DA",
                        style = MaterialTheme.typography.h2,
                        color = Orange,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        TripDestination(name = trip.start, icon = R.drawable.placeholder)
                        Icon(
                            painter = painterResource(R.drawable.dotted_barline),
                            contentDescription = null,
                            modifier = Modifier.height(30.dp),
                            tint = Color.Black
                        )
                        TripDestination(
                            name = trip.destination,
                            icon = R.drawable.destination_pin
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ticket",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black,
                            fontSize = dimensionResource(R.dimen.body1).value.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(40.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(15)
                                )
                                .padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_remove),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        if (tickets > 1) tickets--
                                        price = trip.price * tickets
                                    },
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "$tickets",
                                style = MaterialTheme.typography.body1,
                                fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(R.drawable.ic_add),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        tickets++
                                        price = trip.price * tickets
                                    },
                                tint = Color.Black
                            )
                        }
                    }
                    Button(
                        onClick = {
                            if (tripTime.isNotEmpty()) {
                                val numOfTickets = tickets
                                val date =
                                    "$day/$month/${Calendar.getInstance().get(Calendar.YEAR)}"
                                ticketPostViewModel.postTicket(
                                    date,
                                    time,
                                    numOfTickets,
                                    trip.id
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Choose a time",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(15)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                    ) {
                        Text(
                            text = "Get",
                            style = MaterialTheme.typography.body1,
                            fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                            color = Color.White
                        )
                    }
                }
            }
        } else if (!state.isLoading && state.response != null && !state.response.isSuccessful) {
            Toast.makeText(context, state.response.message(), Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun TripDestination(name: String, icon: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = Orange
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
    }
}
