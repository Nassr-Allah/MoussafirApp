package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.remote.dto.TicketDto
import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val api: MoussafirApi,
) : TicketsRepository {

    override suspend fun getAllTicketsForTrip(id: Int): List<TicketDto> {
        return api.getAllTicketsForTrip(id)
    }

    override suspend fun getAllTicketsForClient(token: String): List<TicketDto> {
        return api.getAllTicketsForClient(token)
    }

    override suspend fun postTicket(
        date: String,
        time: String,
        numOfTickets: Int,
        token: String,
        tripId: Int
    ): Response<Void> {
        return api.addTicket(date, time, numOfTickets, token, tripId)
    }

    override suspend fun updateTicket(id: Int, status: String): Response<Void> {
        return api.updateTicket(id, status)
    }
}