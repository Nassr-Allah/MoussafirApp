package com.ems.moussafirdima.ui.materials

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.screens.main_app.trip.TicketDetailsSection
import com.ems.moussafirdima.ui.theme.GrassGreen
import com.ems.moussafirdima.ui.theme.LightRed
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.theme.VeryLightGray

@Composable
fun ErrorMessage(errorMessage: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "icon",
            tint = Color.Red,
            modifier = Modifier
                .padding(0.dp)
                .size(24.dp)
                .padding(end = 10.dp, bottom = 3.dp)
        )
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.body1,
            color = Color.Red
        )
    }
}

@Composable
fun SuccessMessage(message: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check_circle),
            contentDescription = "icon",
            tint = GrassGreen,
            modifier = Modifier
                .padding(0.dp)
                .size(24.dp)
                .padding(end = 10.dp, bottom = 3.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.body1,
            color = GrassGreen
        )
    }
}

@Composable
fun HaveAnAccountSection(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "have an account?",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "sign in",
            style = MaterialTheme.typography.body1,
            color = Orange,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier.clickable {
                navController.navigate(AuthScreens.LoginScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route)
                }
            }
        )
    }
}

@Composable
fun TransportationMethodBus() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.taxi),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Taxi",
                style = MaterialTheme.typography.h2,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .background(Color.Black)
                .clickable {

                }
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.bus),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Bus",
                style = MaterialTheme.typography.h2,
                color = Color.White,
                fontSize = dimensionResource(R.dimen.body1).value.sp
            )
        }
    }
}

@Composable
fun CustomSnackBar(snackbarHostState: SnackbarHostState, onActionClick: () -> Unit) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                backgroundColor = Color.DarkGray
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.message,
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                    if (data.actionLabel == null) {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = null,
                            tint = Orange,
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Text(
                            text = data.actionLabel ?: "",
                            color = Orange,
                            style = MaterialTheme.typography.h2,
                            fontSize = dimensionResource(R.dimen.body1).value.sp,
                            modifier = Modifier.clickable {
                                onActionClick()
                            }
                        )
                    }
                }
            }
        }
    )
}