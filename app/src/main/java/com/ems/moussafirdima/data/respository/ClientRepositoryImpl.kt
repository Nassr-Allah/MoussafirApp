package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.local.ClientDao
import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.remote.dto.ClientDto
import com.ems.moussafirdima.data.remote.dto.UserDto
import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val api: MoussafirApi,
    private val dao: ClientDao
) : ClientRepository {

    override suspend fun addClient(client: UserDto): ClientDto {
        return api.addClient(client)
    }

    override suspend fun getClientFromDb(): User? {
        return dao.getUser()
    }

    override suspend fun insertClient(client: User) {
        dao.insertUser(client)
    }

    override suspend fun deleteClient() {
        dao.deleteUser()
    }

    override suspend fun getClient(token: String): ClientDto {
        return api.getClient(token)
    }

    override suspend fun loginClient(username: String, password: String): Map<String, String> {
        return api.loginClient(username, password)
    }

    override suspend fun autoLogin(token: String): Response<Void> {
        return api.autoLogin(token)
    }
}