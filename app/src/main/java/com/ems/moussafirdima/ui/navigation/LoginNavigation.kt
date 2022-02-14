package com.ems.moussafirdima.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.screens.login.*
import com.ems.moussafirdima.util.ConnectionLiveData
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@Composable
fun LoginNavigation(isNetworkAvailable: Boolean) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthScreens.SplashScreen.route) {
        composable(route = AuthScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = AuthScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreens.SignUpScreen.route) {
            val user = navController.previousBackStackEntry?.arguments
                ?.getParcelable<User>("user")
            SignUpScreen(navController, user = user)
        }
        composable(route = AuthScreens.CreateProfileScree.route) {
            CreateProfileScreen(navController = navController)
        }
        composable(route = AuthScreens.PasswordScreen.route) {
            val user = navController.previousBackStackEntry?.arguments
                ?.getParcelable<User>("user")
            PasswordScreen(name = "Name", navController = navController, user = user)
        }
        composable(route = AuthScreens.PasswordResetScreen.route) {
            ResetPasswordScreen(navController = navController)
        }
        composable(route = AuthScreens.NewPasswordScreen.route) {
            NewPasswordScreen(navController = navController)
        }
        composable(route = AuthScreens.GenderScreen.route) {
            val user = navController.previousBackStackEntry?.arguments
                ?.getParcelable<User>("user")
            GenderScreen(navController = navController, user = user)
        }
        composable(route = AuthScreens.OtpScreen.route + "/{phone}") {
            val user = navController.previousBackStackEntry?.arguments
                ?.getParcelable<User>("user")
            OtpScreen(navController = navController, user = user)
        }
        composable(route = AuthScreens.OtpResetScreen.route) {
            OtpResetScreen(navController = navController)
        }
        composable(route = AuthScreens.SplashScreen.route) {
            SplashScreen(navController = navController, isNetworkAvailable = isNetworkAvailable)
        }
        composable(route = AuthScreens.ProfilePictureScreen.route) {
            val user = navController.previousBackStackEntry?.arguments
                ?.getParcelable<User>("user")
            ProfilePictureScreen(navController = navController, user = user)
        }
    }
}