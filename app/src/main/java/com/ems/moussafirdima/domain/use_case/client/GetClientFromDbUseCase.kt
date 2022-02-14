package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetClientFromDbUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val client = repository.getClientFromDb()
            emit(Resource.Success<User>(data = client))
        } catch (e: Exception) {
            emit(Resource.Error<User>(message = e.localizedMessage ?: "Unexpected Database Error"))
        }
    }

}