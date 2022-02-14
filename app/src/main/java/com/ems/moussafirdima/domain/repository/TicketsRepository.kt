package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.data.remote.dto.TicketDto
import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.model.Ticket
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TicketsRepository {

    suspend fun getAllTicketsForTrip(id: Int): List<TicketDto>

    suspend fun getAllTicketsForClient(token: String): List<TicketDto>

    suspend fun postTicket(
        date: String,
        time: String,
        numOfTickets: Int,
        token: String,
        tripId: Int
    ): Response<Void>

    suspend fun updateTicket(id: Int, status: String): Response<Void>

}