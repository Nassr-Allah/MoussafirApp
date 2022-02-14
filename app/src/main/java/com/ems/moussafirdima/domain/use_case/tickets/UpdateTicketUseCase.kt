package com.ems.moussafirdima.domain.use_case.tickets

import com.ems.moussafirdima.domain.repository.TicketsRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val repository: TicketsRepository
) {

    operator fun invoke(id: Int, status: String): Flow<Resource<Response<Void>>> = flow {
        try {
            emit(Resource.Loading<Response<Void>>())
            val result = repository.updateTicket(id, status)
            if (result.isSuccessful) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Error<Response<Void>>(result.message()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}