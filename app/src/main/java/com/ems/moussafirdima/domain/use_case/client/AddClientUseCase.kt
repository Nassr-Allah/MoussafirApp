package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.data.remote.dto.UserDto
import com.ems.moussafirdima.data.remote.dto.toUser
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    operator fun invoke(client: UserDto): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val clientDto = repository.addClient(client)
            val user = clientDto.user?.toUser()?.apply {
                token = clientDto.token
            }
            repository.insertClient(user!!)
            emit(Resource.Success<User>(data = user))
        } catch (e: HttpException) {
            emit(Resource.Error<User>(message = e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Resource.Error<User>(message = e.localizedMessage ?: "Unexpected Error"))
        }
    }

}