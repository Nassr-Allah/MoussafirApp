package com.ems.moussafirdima.ui.navigation

sealed class TransportationScreens(val route: String) {
    object TransportationMethodCard : TransportationScreens("transportation_method")
    object BusDestinationCard : TransportationScreens("bus_destination")
    object BusStationCard : TransportationScreens("bus_station_card")
    object DetailsCard : TransportationScreens("details_card")
    object TicketCard : TransportationScreens("ticket_card")
}
