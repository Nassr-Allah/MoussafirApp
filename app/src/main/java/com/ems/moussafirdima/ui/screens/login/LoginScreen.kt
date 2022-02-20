package com.ems.moussafirdima.ui.screens.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.TransportationActivity
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.LightRed
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.client_view_models.LoginClientViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginClientViewModel: LoginClientViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
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
            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.ic_location_pin),
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(R.dimen.thirty_five_dp))
                        )
                    }
                    InfoSection(navController, loginClientViewModel)
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                //SignInMethodsSection()
            }
            Box {
                SignUpSection(navController)
            }
        }

    }
}

@Composable
fun InfoSection(navController: NavController, viewModel: LoginClientViewModel) {
    val context = LocalContext.current.applicationContext
    val state = viewModel.state.value
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var isPhoneError by remember {
        mutableStateOf(false)
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }
    var textAlpha by remember {
        mutableStateOf(1f)
    }
    var progressAlpha by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = state) {
        if (!state.isLoading && state.token.isNotEmpty()) {
            progressAlpha = 0f
            textAlpha = 1f
            val toTransportation = Intent(context, TransportationActivity::class.java)
            toTransportation.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(toTransportation)
        }
        if (state.isLoading) {
            progressAlpha = 1f
            textAlpha = 0f
        }
        if (!state.isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            progressAlpha = 0f
            textAlpha = 1f
        }
    }
    Column {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.ic_path),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )

            Column(modifier = Modifier.padding(horizontal = 35.dp, vertical = 20.dp)) {
                Text(
                    text = stringResource(id = R.string.welcome_back_on_trip),
                    style = MaterialTheme.typography.h1,
                    fontSize = dimensionResource(id = R.dimen.h1).value.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.lets_start_your_journey),
                    style = MaterialTheme.typography.h2,
                    fontSize = dimensionResource(id = R.dimen.h2).value.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        isPhoneError = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.phone_number),
                            style = MaterialTheme.typography.body1,
                            fontSize = dimensionResource(R.dimen.body1).value.sp,
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (isPhoneError) LightRed else Color.Black,
                        unfocusedBorderColor = if (isPhoneError) LightRed else Color.Gray,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon =  {
                        if (isPhoneError) {
                            Icon(
                                painter = painterResource(R.drawable.ic_error),
                                contentDescription = null,
                                tint = LightRed
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (isPhoneError) {
                    Text(
                        text = stringResource(id = R.string.phone_number_is_empty),
                        style = MaterialTheme.typography.body1,
                        color = Color.Red,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isPasswordError = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            style = MaterialTheme.typography.body1,
                            fontSize = dimensionResource(R.dimen.body1).value.sp,
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (isPasswordError) LightRed else Color.Black,
                        unfocusedBorderColor = if (isPasswordError) LightRed else Color.Gray,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        backgroundColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (isPasswordError) {
                            Icon(
                                painter = painterResource(R.drawable.ic_error),
                                contentDescription = null,
                                tint = LightRed
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (isPasswordError) {
                    Text(
                        text = stringResource(id = R.string.password_is_empty),
                        style = MaterialTheme.typography.body1,
                        color = Color.Red,
                        fontSize = dimensionResource(R.dimen.body1).value.sp
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        style = MaterialTheme.typography.body1,
                        fontSize = dimensionResource(R.dimen.body1).value.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            navController.navigate(AuthScreens.PasswordResetScreen.route)
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                if (phoneNumber.isBlank()) {
                    isPhoneError = true
                }
                if (password.isBlank()) {
                    isPasswordError = true
                }
                if (!isPasswordError && !isPhoneError) {
                    Log.d("LoginScreen", "started the call")
                    viewModel.loginClient(phoneNumber, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 35.dp)
                .clip(RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    style = MaterialTheme.typography.body1,
                    fontSize = dimensionResource(id = R.dimen.body1).value.sp,
                    color = Color.White,
                    modifier = Modifier.alpha(textAlpha)
                )
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.alpha(progressAlpha)
                )
            }
        }
    }
}

@Composable
fun SignInMethodsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.fifteen_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Or sign in with",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
                    .padding(horizontal = 5.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.twenty_dp)),
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15))
                    .padding(horizontal = 5.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(R.dimen.twenty_dp))
                )
            }
        }
    }
}

@Composable
fun SignUpSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.dont_have_an_account),
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Orange,
            modifier = Modifier.clickable {
                navController.navigate(AuthScreens.CreateProfileScree.route)
            }
        )
    }

}
