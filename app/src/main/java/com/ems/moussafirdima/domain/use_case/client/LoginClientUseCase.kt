package com.ems.moussafirdima.domain.use_case.client

import android.util.Log
import com.ems.moussafirdima.data.remote.dto.toClient
import com.ems.moussafirdima.data.remote.dto.toUser
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(username: String, password: String): Flow<Resource<String>> = flow {
        Log.d("LoginScreen", "started use case call")
        try {
            emit(Resource.Loading<String>())
            repository.deleteClient()
            val result = repository.loginClient(username, password)
            val tokenKey = result["token"]
            val client = repository.getClient(tokenKey!!)
            val user = client.user!!.toUser().apply {
                token = tokenKey
            }
            Log.d("LoginClientUseCase", user.token)
            repository.insertClient(user)
            emit(Resource.Success<String>(data = tokenKey))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                emit(Resource.Error<String>(message = "Wrong Phone Number or Password"))
            } else {
                emit(Resource.Error<String>(message = e.localizedMessage ?: "Unexpected Server Error"))
            }
        } catch (e: IOException) {
            emit(Resource.Error<String>(message = e.localizedMessage ?: "Unexpected Error"))
        }
    }

}