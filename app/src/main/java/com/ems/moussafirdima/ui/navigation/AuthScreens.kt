package com.ems.moussafirdima.ui.navigation

sealed class AuthScreens(val route: String) {
    object SplashScreen : AuthScreens("splash_screen")
    object HomeScreen : AuthScreens("home_screen")
    object LoginScreen : AuthScreens("login_screen")
    object SignUpScreen : AuthScreens("signup_screen")
    object CreateProfileScree : AuthScreens("create_profile_screen")
    object GenderScreen : AuthScreens("gender_screen")
    object PasswordScreen : AuthScreens("password_screen")
    object NewPasswordScreen : AuthScreens("new_password_screen")
    object PasswordResetScreen : AuthScreens("password_reset_screen")
    object OtpScreen : AuthScreens("otp_screen")
    object OtpResetScreen : AuthScreens("otp_reset_screen")
    object ProfilePictureScreen : AuthScreens("profile_picture_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}