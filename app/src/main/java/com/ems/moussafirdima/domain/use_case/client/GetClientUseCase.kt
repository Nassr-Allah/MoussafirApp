package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.data.remote.dto.ClientDto
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(token: String): Flow<Resource<ClientDto>> = flow {
        try {
            emit(Resource.Loading<ClientDto>())
            val client = repository.getClient(token)
            emit(Resource.Success<ClientDto>(data = client))
        } catch (e: HttpException) {
            emit(Resource.Error<ClientDto>(message = e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<ClientDto>(message = e.localizedMessage ?: "Unexpected Server Error"))
        }
    }

}