package com.ems.moussafirdima.domain.use_case.client

import android.util.Log
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(): Flow<Resource<Response<Void>>> = flow {
        Log.d("AutoLogin", "use case call")
        try {
            emit(Resource.Loading<Response<Void>>())
            val client = repository.getClientFromDb()
            if (client != null) {
                val response = repository.autoLogin(client.token)
                if (response.isSuccessful) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error<Response<Void>>(response.message()))
                }
            } else {
                emit(Resource.Error<Response<Void>>("Empty Db"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}