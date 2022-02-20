package com.ems.moussafirdima.ui.navigation

sealed class MapScreens(val route: String) {
    object MainScreen : MapScreens("main_screen")
    object AccountScreen : MapScreens("account_card")
    object HelpScreen : MapScreens("help_screen")
    object LanguageScreen : MapScreens("language_screen")
    object AboutUsScreen : MapScreens("about_us_screen")
    object TripsScreen : MapScreens("trips_screen")
    object TicketDetailsScreen : MapScreens("ticket_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
