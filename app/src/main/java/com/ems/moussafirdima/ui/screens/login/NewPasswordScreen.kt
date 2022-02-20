package com.ems.moussafirdima.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.materials.ErrorMessage
import com.ems.moussafirdima.ui.materials.SuccessMessage
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.view_models.client_view_models.ChangePasswordViewModel

@ExperimentalAnimationApi
@Composable
fun NewPasswordScreen(
    navController: NavController,
    phoneNumber: String,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val number = phoneNumber.substring(4)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(R.dimen.twenty_five_dp),
                vertical = dimensionResource(R.dimen.twenty_five_dp)
            ),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart)
                .clickable {
                    navController.navigateUp()
                }
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight()
        ) {
            Box {
                NewPasswordScreenHeader()
            }
            PasswordFields(navController, changePasswordViewModel, number)
            Guidelines()
        }
    }

}

@Composable
fun NewPasswordScreenHeader() {
    Column {
        Text(
            text = stringResource(id = R.string.enter),
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        Text(
            text = stringResource(id = R.string.password),
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun PasswordFields(
    navController: NavController,
    viewModel: ChangePasswordViewModel,
    phoneNumber: String
) {
    val context = LocalContext.current.applicationContext
    val state = viewModel.state.value
    var password by remember {
        mutableStateOf("")
    }
    var rePassword by remember {
        mutableStateOf("")
    }
    var visible by remember {
        mutableStateOf(false)
    }
    var alpha by remember {
        mutableStateOf(0f)
    }
    var textAlpha by remember {
        mutableStateOf(1f)
    }
    LaunchedEffect(key1 = state) {
        if (state.response != null && state.response.isSuccessful) {
            alpha = 0f
            textAlpha = 1f
            navController.navigate(AuthScreens.PasswordChangedScreen.route) {
                popUpTo(AuthScreens.HomeScreen.route)
            }
        } else if (state.isLoading) {
            alpha = 1f
            textAlpha = 0f
        } else {
            alpha = 0f
            textAlpha = 1f
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(id = R.string.new_password),
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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.twenty_five_dp)))
        OutlinedTextField(
            value = rePassword,
            onValueChange = {
                rePassword = it
                visible = true
            },
            label = {
                Text(
                    text = stringResource(id = R.string.confirm_password),
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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            if (password == rePassword) {
                SuccessMessage(message = stringResource(id = R.string.passwords_match))
            } else {
                ErrorMessage(errorMessage = stringResource(id = R.string.passwords_dont_match))
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.thirty_five_dp)))
        Button(
            onClick = {
                if (password == rePassword) {
                    Log.d("NewPasswordScreen", phoneNumber)
                    viewModel.changePassword(phoneNumber, password)
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
                color = Color.White,
                modifier = Modifier.alpha(textAlpha)
            )
            CircularProgressIndicator(color = Color.White, modifier = Modifier.alpha(alpha))
        }
    }
}

@Composable
fun Guidelines() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.required_uppercase),
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.required_lowercase),
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.required_numbers),
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
    }
}