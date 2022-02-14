package com.ems.moussafirdima.data.remote.dto

import com.ems.moussafirdima.domain.model.Ticket
import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("time")
    val time: String = "",
    @SerializedName("num_of_tickets")
    val numOfTickets: Int = 1,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("trip")
    val trip: TripDto? = null,
    @SerializedName("client")
    val client: ClientDto? = null,
    @SerializedName("reserved_by")
    val reservedBy: String = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("place_number")
    val placeNumber: String = "",
    @SerializedName("number")
    val number: String = ""
)

fun TicketDto.toTicket(): Ticket {
    return Ticket(
        id,
        date,
        time,
        numOfTickets,
        status,
        trip?.toTrip(),
        client?.toClient(),
        reservedBy,
        price,
        placeNumber,
        number
    )
}
