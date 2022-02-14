package com.ems.moussafirdima.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            TopSection()
        }
        BottomSection(navController)
    }
}

@Composable
fun TopSection() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_home_illustration),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.ten_dp))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
        Text(
            text = "Moussafir",
            style = MaterialTheme.typography.h1,
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.h1).value.sp,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.fifty_dp))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Let's take a long trip",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.h2).value.sp,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.fifty_dp))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Enjoy!",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.h2).value.sp,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.fifty_dp))
        )
    }
}

@Composable
fun BottomSection(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "sign up",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.body1).value.sp,
            modifier = Modifier.clickable {
                navController.navigate(AuthScreens.CreateProfileScree.route)
            }
        )
        Button(
            onClick = {
                navController.navigate(AuthScreens.LoginScreen.route)
            },
            modifier = Modifier.clip(RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(
                text = "sign in",
                style = MaterialTheme.typography.body1,
                color = Color.White,
                fontSize = dimensionResource(id = R.dimen.body1).value.sp
            )
        }
    }
}

/*
@Preview(device = Devices.NEXUS_5)
@Composable
fun HomeScreenPreview() {
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            HomeScreen()
        }
    }
}

 */