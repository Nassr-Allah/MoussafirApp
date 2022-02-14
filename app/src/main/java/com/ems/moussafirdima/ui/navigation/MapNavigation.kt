package com.ems.moussafirdima.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.ui.screens.main_app.MapScreen
import com.ems.moussafirdima.ui.screens.main_app.profile.AccountScreen
import com.ems.moussafirdima.ui.screens.main_app.profile.LanguageScreen
import com.ems.moussafirdima.ui.screens.main_app.profile.SupportScreen
import com.ems.moussafirdima.ui.screens.main_app.trip.TicketDetailsScreen
import com.ems.moussafirdima.ui.screens.main_app.trip.TripsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.libraries.maps.GoogleMap

@ExperimentalPermissionsApi
@Composable
fun MapNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MapScreens.MainScreen.route) {
        composable(route = MapScreens.MainScreen.route) {
            MapScreen(navController = navController)
        }
        composable(route = MapScreens.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(route = MapScreens.HelpScreen.route) {
            SupportScreen(navController = navController)
        }
        composable(route = MapScreens.LanguageScreen.route) {
            LanguageScreen(navController = navController)
        }
        composable(route = MapScreens.TripsScreen.route + "/{token}") {
            TripsScreen(navController = navController)
        }
        composable(route = MapScreens.TicketDetailsScreen.route) {
            val ticket = navController.previousBackStackEntry?.arguments
                ?.getParcelable<Ticket>("ticket")
            TicketDetailsScreen(ticket = ticket)
        }
    }
}