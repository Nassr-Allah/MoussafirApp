package com.ems.moussafirdima.ui.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.PhoneVerificationViewModel
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    navController: NavController,
    user: User?,
    phoneVerificationViewModel: PhoneVerificationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current.applicationContext
    val state = phoneVerificationViewModel.state.value
    Log.d("OtpScreen", state.toString())
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 15.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (!state.isLoading && state.code.isNotEmpty()) {
                Log.d("OtpScreen", state.toString())
                ScreenComponents(navController, state.code, context, phoneVerificationViewModel, user)
            }
            else if (state.isLoading) {
                ScreenComponents(navController, "", context, phoneVerificationViewModel, user)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Orange)
                }
            } else {
                ScreenComponents(navController, "", context, phoneVerificationViewModel, user)
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ScreenComponents(navController: NavController, code: String, context: Context,
                     phoneVerificationViewModel: PhoneVerificationViewModel, user: User?) {
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
    OtpScreenHeader()
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
    CodeFields(code = code, navController, context, phoneVerificationViewModel, user)
}

@Composable
fun OtpScreenHeader() {
    Column {
        Text(
            text = stringResource(id = R.string.otp_verification),
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h1).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        Text(
            text = stringResource(id = R.string.type_the_code_sent_via_sms),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Composable
fun CodeFields(code: String, navController: NavController, context: Context,
               phoneVerificationViewModel: PhoneVerificationViewModel, user: User?) {
    var smsCode by remember {
        mutableStateOf("000000")
    }
    var savedCode = code
    var resendCode by remember {
        mutableStateOf(context.getString(R.string.resend_code))
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
    val focusRequester = remember {
        FocusRequester()
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
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        focusRequester.requestFocus()
                    })
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
                    ),
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
                text = if (currentTime == 0L) resendCode else (currentTime/1000L).toString() ,
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
                    navController.currentBackStackEntry?.arguments?.putParcelable("user", user)
                    navController.navigate(AuthScreens.PasswordScreen.route) {
                        popUpTo(AuthScreens.CreateProfileScree.route)
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.wrong_code), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = stringResource(id = R.string.confirm),
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                color = Color.White
            )
        }
    }
}
