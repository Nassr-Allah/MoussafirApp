package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(phoneNumber: String, password: String): Flow<Resource<Response<Void>>> = flow {
        try {
            emit(Resource.Loading<Response<Void>>())
            val result = repository.changePassword(phoneNumber, password)
            if (result.isSuccessful && result.code() == 202) {
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