package com.ems.moussafirdima.domain.use_case.tickets

import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.domain.repository.TicketsRepository
import com.ems.moussafirdima.util.Resource
import com.ems.moussafirdima.util.isDateMoreThanOneDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PostTicketUseCase @Inject constructor(
    private val repository: TicketsRepository,
    private val clientRepository: ClientRepository
) {

    operator fun invoke(
        date: String,
        time: String,
        numOfTickets: Int,
        tripId: Int
    ) : Flow<Resource<Response<Void>>> = flow {
        try {
            emit(Resource.Loading<Response<Void>>())
            val client = clientRepository.getClientFromDb()
            val token = client!!.token
            val response = repository.postTicket(date, time, numOfTickets, token, tripId)
            if (!isDateMoreThanOneDay(date)) {
                emit(Resource.Success<Response<Void>>(response))
            } else {
                emit(Resource.Error<Response<Void>>("Date Error"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Server Error"))
        }
    }
}