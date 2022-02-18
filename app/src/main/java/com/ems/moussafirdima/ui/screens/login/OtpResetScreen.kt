package com.ems.moussafirdima.ui.screens.login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.PhoneVerificationViewModel
import kotlinx.coroutines.delay


@Composable
fun OtpResetScreen(
    navController: NavController,
    phoneVerificationViewModel: PhoneVerificationViewModel = hiltViewModel(),
    phoneNumber: String
) {
    val state = phoneVerificationViewModel.state.value
    val context = LocalContext.current.applicationContext
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 15.dp)
    ) {
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
            OtpResetScreenHeader()
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
            if (!state.isLoading && state.code.isNotEmpty()) {
                CodeResetFields(code = state.code, navController, phoneVerificationViewModel, phoneNumber)
            } else if (state.isLoading) {
                CodeResetFields(code = "", navController, phoneVerificationViewModel, phoneNumber)
            } else if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
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
fun CodeResetFields(
    code: String,
    navController: NavController,
    phoneVerificationViewModel: PhoneVerificationViewModel,
    phoneNumber: String
) {
    val context = LocalContext.current.applicationContext
    var smsCode by remember {
        mutableStateOf("000000")
    }
    var savedCode = code
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
    val totalTime = 60000L
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    LaunchedEffect(key1 = currentTime) {
        if (currentTime > 0) {
            delay(100L)
            currentTime -= 100L
        }
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
                text = if (currentTime == 0L) "Resend Code" else (currentTime / 1000L).toString(),
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                modifier = Modifier.clickable {
                    if (currentTime == 0L) {
                        phoneVerificationViewModel.recall()
                        savedCode = phoneVerificationViewModel.state.value.code
                        currentTime = 60000L
                    }
                }
            )
        }
        Button(
            onClick = {
                val inputCode = "$firstDigit$secondDigit$thirdDigit$fourthDigit$fifthDigit$sixthDigit"
                if (inputCode == savedCode) {
                    navController.navigate(AuthScreens.NewPasswordScreen.withArgs(phoneNumber)) {
                        popUpTo(AuthScreens.PasswordResetScreen.route)
                    }
                } else {
                    Toast.makeText(context, "Wrong Code!", Toast.LENGTH_SHORT).show()
                }
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
