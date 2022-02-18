package com.ems.moussafirdima.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.materials.HaveAnAccountSection
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange


@Composable
fun CreateProfileScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
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
            InputFormSection(navController)
        }
    }
}

@Composable
fun CreateProfileHeader() {
    Text(
        text = "Create your journey",
        style = MaterialTheme.typography.h1,
        color = Color.Black,
        fontSize = dimensionResource(R.dimen.h1).value.sp
    )
}

@Composable
fun InputFormSection(navController: NavController) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CreateProfileHeader()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = {
                    Text(
                        text = "First Name",
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(R.dimen.body1).value.sp,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(150.dp),
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = {
                    Text(
                        text = "Last Name",
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(R.dimen.body1).value.sp,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(150.dp),
            )
        }

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            label = {
                Text(
                    text = "Phone Number",
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(R.dimen.body1).value.sp,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Text(
                    text = "+213",
                    style = MaterialTheme.typography.body1,
                    color = Orange,
                    fontSize = dimensionResource(R.dimen.body1).value.sp
                )
            }
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(R.dimen.body1).value.sp,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = location,
            onValueChange = {
                location = it
            },
            label = {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(R.dimen.body1).value.sp,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )

        Button(
            onClick = {
                val user = User(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = "0$phoneNumber",
                    email = email,
                    address = location
                )
                navController.currentBackStackEntry?.arguments?.putParcelable("user", user)
                navController.navigate(AuthScreens.OtpScreen.withArgs("+213$phoneNumber"))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = "next",
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                color = Color.White
            )
        }
        HaveAnAccountSection(navController)
    }
}

/*
@Preview(device = Devices.NEXUS_5)
@Composable
fun CreateProfilePreview() {
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            CreateProfileScreen()
        }
    }
}

 */

