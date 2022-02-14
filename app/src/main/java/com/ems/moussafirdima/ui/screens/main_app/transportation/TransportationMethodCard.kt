package com.ems.moussafirdima.ui.screens.main_app.transportation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.navigation.TransportationScreens

@Composable
fun TransportationMethodCard(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20)),
        elevation = 50.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceAround
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
                    style = MaterialTheme.typography.h1,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navController.navigate(TransportationScreens.BusDestinationCard.route)
                }) {
                Icon(
                    painter = painterResource(R.drawable.bus),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Bus",
                    style = MaterialTheme.typography.h1,
                    color = Color.Black,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun TransportationCardPreview() {
    TransportationMethodCard()
}

 */