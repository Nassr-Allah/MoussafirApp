package com.ems.moussafirdima.ui.screens.main_app.trip

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.ui.materials.FilterItem
import com.ems.moussafirdima.ui.navigation.MapScreens
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.TimeBox
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.time
import com.ems.moussafirdima.ui.theme.GrassGreen
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.ticket_view_models.TicketsListViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

var ticketsList: MutableList<RoomTicket> = mutableListOf()

@Composable
fun TripsScreen(
    ticketsListViewModel: TicketsListViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = ticketsListViewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.tickets),
                style = MaterialTheme.typography.h1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.h1).value.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            FiltersList(
                list = listOf(
                    stringResource(R.string.all),
                    stringResource(R.string.today),
                    stringResource(R.string.this_week),
                    stringResource(R.string.this_month),
                    stringResource(R.string.paid),
                    stringResource(R.string.canceled)
                ),
                viewModel = ticketsListViewModel
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (state.tickets.isNotEmpty()) {
                TripsList(list = state.tickets, navController = navController)
            } else if (!state.isLoading && state.tickets.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_tickets_found),
                        style = MaterialTheme.typography.h2,
                        color = Color.Black,
                        fontSize = dimensionResource(R.dimen.h1).value.sp
                    )
                }
            } else if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Orange)
                }
            }
        }
    }

}

@Composable
fun TripsList(list: List<Ticket>, navController: NavController) {
    LazyColumn {
        items(list.size) {
            TripCard(ticket = list[it], navController = navController)
        }
    }
}

@Composable
fun TripCard(ticket: Ticket, navController: NavController) {
    val textColor = when (ticket.status) {
        stringResource(R.string.paid) -> GrassGreen
        else -> Color.Red
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clickable {
                navController.currentBackStackEntry?.arguments?.putParcelable("ticket", ticket)
                navController.navigate(MapScreens.TicketDetailsScreen.route)
            },
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${ticket.trip?.start}",
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "${ticket.trip?.destination}",
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(35.dp)
                    ) {
                        GlideImage(
                            imageModel = ticket.client?.user?.profilePicture,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = "${ticket.client?.user?.firstName} ${ticket.client?.user?.lastName}",
                            style = MaterialTheme.typography.h2,
                            color = Color.Black,
                            fontSize = dimensionResource(R.dimen.body2).value.sp
                        )
                        Text(
                            text = "${ticket.price.toString()} DA",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black,
                            fontSize = dimensionResource(R.dimen.body2).value.sp,
                            modifier = Modifier.alpha(0.8f)
                        )
                    }
                }
                Text(
                    text = ticket.status,
                    style = MaterialTheme.typography.h2,
                    color = textColor,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
        }
    }
}

@Composable
fun FiltersList(list: List<String>, viewModel: TicketsListViewModel) {
    var currentItem by remember {
        mutableStateOf(0)
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(list.size) {
            FilterBox(filter = list[it], isSelected = it == currentItem) {
                currentItem = it
                viewModel.applyFilter(list[it])
            }
        }
    }
}

@Composable
fun FilterBox(filter: String, isSelected: Boolean = false, onItemClick: () -> Unit) {
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
            text = filter,
            style = MaterialTheme.typography.body1,
            color = textColor,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
    }
}