package com.ems.moussafirdima.domain.use_case.tickets

import com.ems.moussafirdima.data.remote.dto.toTicket
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.domain.repository.TicketsRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetClientTicketsUseCase @Inject constructor(
    private val repository: TicketsRepository,
) {

    operator fun invoke(token: String): Flow<Resource<List<Ticket>>> = flow {
        try {
            emit(Resource.Loading<List<Ticket>>())
            val tickets = repository.getAllTicketsForClient(token).map { it.toTicket() }
            emit(Resource.Success(tickets))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Ticket>>(e.localizedMessage ?: "An Unexpected Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Ticket>>("Couldn't reach the server. Check your internet connection"))
        }
    }
}