package com.ems.moussafirdima.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.ui.screens.main_app.transportation.TransportationMethodCard
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.BusDestinationCard
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.BusStationCard
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.DetailsCard
import com.ems.moussafirdima.ui.screens.main_app.transportation.bus.TicketInterface

@Composable
fun TransportationNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TransportationScreens.TransportationMethodCard.route
    ) {
        composable(route = TransportationScreens.TransportationMethodCard.route) {
            TransportationMethodCard(navController = navController)
        }
        composable(route = TransportationScreens.BusDestinationCard.route) {
            BusDestinationCard(navController = navController)
        }
        composable(
            route = TransportationScreens.DetailsCard.route + "/{start}" + "/{destination}",
            arguments = listOf(
                navArgument("ticket") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val station = navController.previousBackStackEntry?.arguments?.getParcelable<Station>("station")
            DetailsCard(navController = navController, station = station)
        }
        composable(route = TransportationScreens.BusStationCard.route + "/{destination}",) {
            BusStationCard(
                navController = navController,
                destination = it.arguments?.getString("destination")
            )
        }
        composable(route = TransportationScreens.TicketCard.route) {
            TicketInterface(navController = navController)
        }
    }
}