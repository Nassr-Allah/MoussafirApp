package com.ems.moussafirdima.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.materials.ErrorMessage
import com.ems.moussafirdima.ui.materials.SuccessMessage
import com.ems.moussafirdima.ui.navigation.AuthScreens

@ExperimentalAnimationApi
@Composable
fun NewPasswordScreen(navController: NavController) {
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
            modifier = Modifier.size(30.dp).align(Alignment.TopStart).clickable {
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
            PasswordFields(navController)
            Guidelines()
        }
    }

}

@Composable
fun NewPasswordScreenHeader() {
    Column {
        Text(
            text = "Enter",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        Text(
            text = "Password",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h0).value.sp
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun PasswordFields(navController: NavController) {
    var password by remember {
        mutableStateOf("")
    }
    var rePassword by remember {
        mutableStateOf("")
    }
    var visible by remember {
        mutableStateOf(false)
    }
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "New Password",
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
                    text = "Confirm Password",
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
                SuccessMessage(message = "Passwords Match")
            } else {
                ErrorMessage(errorMessage = "Passwords don't match")
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.thirty_five_dp)))
        Button(
            onClick = {
                navController.navigate(AuthScreens.LoginScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route)
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

@Composable
fun Guidelines() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Required (A-Z) letter",
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Required (a-z) letter",
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Required numbers (0-9)",
            style = MaterialTheme.typography.body1,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            color = Color.Black
        )
    }
}

@Composable
fun PasswordCheckIcon() {
    Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(R.drawable.check),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(30.dp)
        )
    }
}

/*
@ExperimentalAnimationApi
@Preview(device = Devices.NEXUS_5)
@Composable
fun NewPasswordPreview() {
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            NewPasswordScreen()
        }
    }
}

 */