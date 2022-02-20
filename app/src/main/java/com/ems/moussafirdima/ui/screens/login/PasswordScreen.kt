package com.ems.moussafirdima.ui.screens.login

import android.widget.Toast
import androidx.compose.animation.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.materials.ErrorMessage
import com.ems.moussafirdima.ui.materials.HaveAnAccountSection
import com.ems.moussafirdima.ui.materials.SuccessMessage
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange

@ExperimentalAnimationApi
@Composable
fun PasswordScreen(name: String, navController: NavController, user: User?) {
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
                .clickable { navController.navigateUp() }
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight()
        ) {
            Box {
                PasswordScreenHeader(name)
            }
            PasswordTextFields(navController, user)
            HaveAnAccountSection(navController)
        }
    }

}

@Composable
fun PasswordScreenHeader(name: String) {
    Column {
        Text(
            text = "${stringResource(id = R.string.hello)} $name !",
            style = MaterialTheme.typography.h2,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.h1).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.five_dp)))
        Text(
            text = stringResource(id = R.string.complete_your_registration),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.body1).value.sp,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun PasswordTextFields(navController: NavController, user: User?) {
    var password by remember {
        mutableStateOf("")
    }
    var rePassword by remember {
        mutableStateOf("")
    }
    var visible by remember {
        mutableStateOf(false)
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val iconTint by animateColorAsState(
        if (passwordVisibility) Color.Black else Color.Gray
    )
    val keyboardOptions = if (passwordVisibility) {
        KeyboardOptions(keyboardType = KeyboardType.Password)
    } else {
        KeyboardOptions.Default
    }
    val context = LocalContext.current.applicationContext
    Column {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(id = R.string.password),
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
            visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.clickable {
                        passwordVisibility = !passwordVisibility
                    }
                )
            }
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
            modifier = Modifier
                .fillMaxWidth(),
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
        PasswordGuidelines()
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.thirty_five_dp)))
        Button(
            onClick = {
                if (password == rePassword) {
                    user?.password = password
                    navController.currentBackStackEntry?.arguments?.putParcelable("user", user)
                    navController.navigate(AuthScreens.GenderScreen.route)
                } else {
                    Toast.makeText(context, context.getString(R.string.passwords_dont_match), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(R.dimen.body1).value.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun PasswordGuidelines() {
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


/*
@ExperimentalAnimationApi
@Preview()
@Composable
fun PasswordPreview() {
    MoussafirDimaTheme {
        Surface(color = MaterialTheme.colors.background) {
            PasswordScreen("Nassr-Allah Guetatlia")
        }
    }
}

 */