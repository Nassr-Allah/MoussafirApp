package com.ems.moussafirdima.ui.screens.main_app.transportation.bus

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.ui.navigation.TransportationScreens
import com.ems.moussafirdima.ui.theme.GrassGreen
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun TicketInterface(navController: NavController) {
    val alpha = remember {
        Animatable(0f)
    }
    val buttonAlpha  = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        buttonAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
    }
    Card(
        modifier = Modifier
            .fillMaxHeight(0.2f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = null,
                    tint = GrassGreen,
                    modifier = Modifier
                        .size(30.dp)
                        .alpha(alpha.value)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.ticket_added_successfully),
                    style = MaterialTheme.typography.h2,
                    fontSize = dimensionResource(R.dimen.body1).value.sp,
                    color = GrassGreen,
                    modifier = Modifier.alpha(alpha.value)
                )
            }
            Button(
                onClick = {
                    navController.navigate(TransportationScreens.BusDestinationCard.route) {
                        popUpTo(TransportationScreens.BusDestinationCard.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(15))
                    .alpha(buttonAlpha.value),
                colors = ButtonDefaults.buttonColors(backgroundColor = GrassGreen)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}