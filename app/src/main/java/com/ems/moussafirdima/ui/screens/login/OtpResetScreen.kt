package com.ems.moussafirdima.ui.screens.login

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
import com.ems.moussafirdima.ui.theme.Orange


@Composable
fun OtpResetScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 15.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.size(30.dp).clickable {
                    navController.navigateUp()
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
            OtpResetScreenHeader()
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
            CodeResetFields(code = "123456", navController)
        }
    }
}

@Composable
fun OtpResetScreenHeader() {
    Column {
        Text(
            text = "OTP Verification",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h1).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        Text(
            text = "Type the code sent via SMS",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Composable
fun CodeResetFields(code: String, navController: NavController) {
    var smsCode by remember {
        mutableStateOf(code)
    }
    var resendCode by remember {
        mutableStateOf("Resend Code")
    }
    var firstDigit by remember {
        mutableStateOf(smsCode[0])
    }
    var secondDigit by remember {
        mutableStateOf(smsCode[1])
    }
    var thirdDigit by remember {
        mutableStateOf(smsCode[2])
    }
    var fourthDigit by remember {
        mutableStateOf(smsCode[3])
    }
    var fifthDigit by remember {
        mutableStateOf(smsCode[4])
    }
    var sixthDigit by remember {
        mutableStateOf(smsCode[5])
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 30.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = firstDigit.toString(),
                    onValueChange = {
                        firstDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = secondDigit.toString(),
                    onValueChange = {
                        secondDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = thirdDigit.toString(),
                    onValueChange = {
                        thirdDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    ),
                )
                OutlinedTextField(
                    value = fourthDigit.toString(),
                    onValueChange = {
                        fourthDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = fifthDigit.toString(),
                    onValueChange = {
                        fifthDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = sixthDigit.toString(),
                    onValueChange = {
                        sixthDigit = if (it.isEmpty()) {
                            ' '
                        } else {
                            it[0]
                        }
                    },
                    modifier = Modifier
                        .width(40.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedBorderColor = Orange,
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_dp)))
            Text(
                text = resendCode,
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp
            )
        }
        Button(
            onClick = {
                navController.navigate(AuthScreens.NewPasswordScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = "Confirm",
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                color = Color.White
            )
        }
    }
}

/*
@Preview()
@Composable
fun OtpPreview() {
    MoussafirDimaTheme() {
        Surface(color = MaterialTheme.colors.background) {
            OtpScreen()
        }
    }
}

 */