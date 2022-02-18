package com.ems.moussafirdima.ui.screens.login

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ems.moussafirdima.R
import com.ems.moussafirdima.ui.navigation.AuthScreens
import com.ems.moussafirdima.ui.theme.GrassGreen
import kotlinx.coroutines.delay

@Composable
fun PasswordChangedScreen(navController: NavController) {
    var textAlpha = remember {
        Animatable(0f)
    }
    var iconAlpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        iconAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(1f).getInterpolation(it)
                }
            )
        )
        delay(1500)
        navController.navigate(AuthScreens.LoginScreen.route) {
            popUpTo(AuthScreens.HomeScreen.route)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Password Changed Successfully!",
                style = MaterialTheme.typography.h2,
                color = GrassGreen,
                fontSize = dimensionResource(R.dimen.h2).value.sp,
                modifier = Modifier.alpha(textAlpha.value)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painter = painterResource(R.drawable.ic_check_circle),
                contentDescription = null,
                tint = GrassGreen,
                modifier = Modifier.size(50.dp).alpha(iconAlpha.value)
            )
        }
    }
}