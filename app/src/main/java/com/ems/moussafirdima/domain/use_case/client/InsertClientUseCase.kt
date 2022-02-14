package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.repository.ClientRepository
import javax.inject.Inject

class InsertClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    suspend operator fun invoke(client: User) {
        repository.insertClient(client)
    }

}