package com.ems.moussafirdima.ui.screens.main_app.trip

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.ui.materials.CustomSnackBar
import com.ems.moussafirdima.ui.theme.*
import com.ems.moussafirdima.ui.view_models.ticket_view_models.TicketUpdateViewModel

@Composable
fun TicketDetailsScreen(
    ticket: Ticket?,
    viewModel: TicketUpdateViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            CustomSnackBar(snackbarHostState = it) {
                viewModel.updateTicket(ticket!!.id, "paid")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (ticket != null) {
                    TicketHeader(ticket = ticket)
                    TicketSection(ticket, viewModel, scaffoldState)
                }
                Card(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    elevation = 0.dp
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_moussafir),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Composable
fun TicketHeader(ticket: Ticket?) {
    val name = if (ticket != null) {
        "${ticket.client!!.user!!.firstName} ${ticket.client.user!!.lastName}"
    } else {
        ""
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h2).value.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_location_point),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = ticket?.trip!!.start,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body2).value.sp
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_location_point),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = ticket?.trip!!.destination,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Destination",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body2).value.sp
                )
            }
        }
    }
}

@Composable
fun TicketDetailsSection(ticket: Ticket?) {
    val name = "${ticket?.client!!.user!!.firstName} ${ticket.client.user!!.lastName}"
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            TicketDetail(title = "Passenger", info = name)
            Spacer(modifier = Modifier.height(20.dp))
            TicketDetail(title = "Bus Driver", info = ticket.trip!!.driver)
            Spacer(modifier = Modifier.height(20.dp))
            TicketDetail(title = "Price", info = "${ticket.price} DA")
        }
        Column {
            TicketDetail(title = "Date", info = "${ticket.date} - ${ticket.time}")
            Spacer(modifier = Modifier.height(20.dp))
            TicketDetail(title = "Reserved By", info = ticket.reservedBy)
            Spacer(modifier = Modifier.height(20.dp))
            TicketDetail(title = "Place", info = ticket.placeNumber)
        }
    }
}

@Composable
fun TicketDetail(title: String, info: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier.alpha(0.5f)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = info,
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
        )
    }
}

@Composable
fun TicketSection(
    ticket: Ticket?,
    viewModel: TicketUpdateViewModel,
    scaffoldState: ScaffoldState
) {
    val state = viewModel.state.value
    val statusColor = if (ticket?.status == "paid") {
        GrassGreen
    } else {
        LightRed
    }
    var status by remember {
        mutableStateOf(ticket!!.status)
    }
    LaunchedEffect(key1 = state) {
        if (!state.isLoading && state.response != null && state.response.isSuccessful) {
            if (ticket!!.status == "paid") {
                ticket.status = "canceled"
                status = "canceled"
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Ticket Canceled",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Long
                )
            } else {
                ticket.status = "paid"
                status = "paid"
                Log.d("TicketStatus", "Paid")
            }
        }
    }
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(VeryLightGray)
            .padding(25.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.h2,
                color = statusColor,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = statusColor,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = ticket?.trip!!.start,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_ticket_bus),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = ticket?.trip.destination,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${ticket?.number}",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h2).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_dp)))
        TicketDetailsSection(ticket = ticket)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.fifteen_dp)))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CANCEL",
                style = MaterialTheme.typography.h2,
                color = LightRed,
                fontSize = dimensionResource(R.dimen.h2).value.sp,
                modifier = Modifier.clickable {
                    viewModel.updateTicket(ticket!!.id, "canceled")
                }
            )
        }
    }
}



