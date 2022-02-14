package com.ems.moussafirdima.ui.screens.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.TransportationActivity
import com.ems.moussafirdima.ui.materials.CustomSnackBar
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.MoussafirDimaTheme
import com.ems.moussafirdima.ui.theme.Orange
import com.ems.moussafirdima.ui.view_models.client_view_models.AutoLoginViewModel
import com.ems.moussafirdima.ui.view_models.states.ResponseState
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun SplashScreen(
    navController: NavController,
    autoLoginViewModel: AutoLoginViewModel = hiltViewModel(),
    isNetworkAvailable: Boolean
) {
    val state = autoLoginViewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        MapAnimation(navController, state, isNetworkAvailable)
        Row(modifier = Modifier.padding(start = 50.dp)) {
            PinAnimation()
            MAnimation()
            MoussafirAnimation()
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun PinAnimation() {
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1000)
    }
    Image(
        painter = painterResource(R.drawable.ic_pin),
        contentDescription = null,
        modifier = Modifier.alpha(alpha.value)
    )

}

@ExperimentalAnimationApi
@Composable
fun MAnimation() {
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        delay(800)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )

    }
    Icon(
        painter = painterResource(R.drawable.ic_m),
        contentDescription = null,
        modifier = Modifier.alpha(alpha.value),
        tint = Color.Black
    )

}

@ExperimentalAnimationApi
@Composable
fun MoussafirAnimation() {
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        delay(1800)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
    }
    Text(
        text = "Moussafir",
        style = MaterialTheme.typography.h1,
        fontSize = dimensionResource(R.dimen.h0).value.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(start = 10.dp)
            .alpha(alpha.value)
    )
}

@Composable
fun MapAnimation(navController: NavController, state: ResponseState, isNetworkAvailable: Boolean) {
    val alpha = remember {
        Animatable(0f)
    }
    var isReady by remember {
        mutableStateOf(false)
    }
    var timeLimit by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current.applicationContext
    Image(
        painter = painterResource(R.drawable.ic_map),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.alpha(alpha.value)
    )
    LaunchedEffect(key1 = true) {
        delay(2800)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        delay(200)
        isReady = true
        while (timeLimit < 3) {
            delay(1000)
            timeLimit++
        }
    }
    LaunchedEffect(key1 = state, key2 = isReady, key3 = timeLimit) {
        if (isReady) {
            if (!state.isLoading && state.error == "Empty Db") {
                navController.popBackStack()
                navController.navigate(AuthScreens.HomeScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
            if (!state.isLoading && state.response != null && state.response.code() == 202) {
                val toApplication = Intent(context, TransportationActivity::class.java)
                toApplication.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                navController.popBackStack()
                navController.navigate(AuthScreens.HomeScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route) {
                        inclusive = true
                    }
                }
                context.startActivity(toApplication)
            }
            if (!state.isLoading && state.response != null
                && !state.response.isSuccessful || state.response?.code() == 404
            ) {
                navController.popBackStack()
                navController.navigate(AuthScreens.HomeScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
            if (timeLimit == 2) {
                navController.popBackStack()
                navController.navigate(AuthScreens.HomeScreen.route) {
                    popUpTo(AuthScreens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

