package com.ems.moussafirdima.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.materials.ErrorMessage
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme

@ExperimentalAnimationApi
@Composable
fun ResetPasswordScreen(navController: NavController) {
    var phoneNumber by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .padding(
                horizontal = 50.dp,
                vertical = 25.dp
            )
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clickable {
                            navController.navigateUp()
                        }
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
                    PasswordResetScreenHeader()
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.fifteen_dp)))
                    Image(
                        painter = painterResource(id = R.drawable.ic_pass_reset_illustration),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            Box {
                TextFieldAndButtonSection(navController)
            }
        }
    }
}

@Composable
fun PasswordResetScreenHeader() {
    Text(
        text = "Reset your password",
        style = MaterialTheme.typography.h1,
        color = Color.Black,
        fontSize = dimensionResource(R.dimen.h1).value.sp
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.fifteen_dp)))
    Text(
        text = "Enter your phone number",
        style = MaterialTheme.typography.h2,
        color = Color.Black,
        fontSize = dimensionResource(R.dimen.h2).value.sp
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.ten_dp)))
    Text(
        text = "to reset password",
        style = MaterialTheme.typography.h2,
        color = Color.Black,
        fontSize = dimensionResource(R.dimen.h2).value.sp
    )
}

@ExperimentalAnimationApi
@Composable
fun TextFieldAndButtonSection(navController: NavController) {

    var phoneNumber by remember {
        mutableStateOf("")
    }
    var visible by remember {
        mutableStateOf(false)
    }

    Column {

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                visible = false
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
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorMessage(errorMessage = "Phone Number is empty")
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.twenty_five_dp)))
        Button(
            onClick = {
                if (phoneNumber.isEmpty()) {
                    visible = true
                } else {
                    visible = false
                    navController.navigate(AuthScreens.OtpResetScreen.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.body1,
                color = Color.White,
                fontSize = dimensionResource(R.dimen.body1).value.sp
            )
        }
    }
}

/*
@ExperimentalAnimationApi
@Preview()
@Composable
fun ResetPasswordPreview() {
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            ResetPasswordScreen()
        }
    }
}

 */