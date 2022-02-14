package com.ems.moussafirdima.ui.screens.login

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange

@Composable
fun GenderScreen(navController: NavController, user: User?) {
    Box(modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
            Text(
                text = "Which one are you",
                style = MaterialTheme.typography.h1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.h0).value.sp
            )
            GenderSelectionSection(navController, user)
        }
    }
}

@Composable
fun GenderSelectionSection(navController: NavController, user: User?) {
    var male by remember {
        mutableStateOf(false)
    }
    var female by remember {
        mutableStateOf(false)
    }
    val maleBackgroundColor by animateColorAsState(
        if (male) Orange else Color.Transparent
    )
    val femaleBackgroundColor by animateColorAsState(
        if (female) Orange else Color.Transparent
    )
    val maleTextColor by animateColorAsState(
        if (male) Color.White else Color.Gray
    )
    val femaleTextColor by animateColorAsState(
        if (female) Color.White else Color.Gray
    )
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
                    .background(maleBackgroundColor)
                    .clickable {
                        male = !male
                        female = false
                        user?.gender = "male"
                    }
                    .padding(vertical = 15.dp)
                    .width(130.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Male",
                    style = MaterialTheme.typography.body1,
                    color = maleTextColor,
                    fontSize = dimensionResource(R.dimen.h1).value.sp
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
                    .background(femaleBackgroundColor)
                    .clickable {
                        female = !female
                        male = false
                        user?.gender = "female"
                    }
                    .padding(vertical = 15.dp)
                    .width(130.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Female",
                    style = MaterialTheme.typography.body1,
                    color = femaleTextColor,
                    fontSize = dimensionResource(R.dimen.h1).value.sp
                )
            }
        }
        Button(
            onClick = {
                if (male || female) {
                    navController.currentBackStackEntry?.arguments?.putParcelable("user", user)
                    navController.navigate(AuthScreens.ProfilePictureScreen.route)
                } else {
                    Toast.makeText(context, "Pick a Gender!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = "Confirm",
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                color = Color.White
            )
        }
    }
}

/*
@Preview(device = Devices.NEXUS_5)
@Composable
fun GenderPreview() {
    MoussafirDimaTheme() {
        Surface(color = MaterialTheme.colors.background) {
            GenderScreen()
        }
    }
}

 */
