package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.data.remote.dto.ClientDto
import com.ems.moussafirdima.data.remote.dto.UserDto
import com.ems.moussafirdima.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ClientRepository {

    suspend fun addClient(client: UserDto): ClientDto

    suspend fun getClientFromDb(): User?

    suspend fun insertClient(client: User)

    suspend fun deleteClient()

    suspend fun getClient(token: String): ClientDto

    suspend fun loginClient(username: String, password: String): Map<String, String>

    suspend fun autoLogin(token: String): Response<Void>

    suspend fun changePassword(phoneNumber: String, password: String): Response<Void>

}