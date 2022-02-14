package com.ems.moussafirdima.domain.use_case.client

import com.ems.moussafirdima.domain.model.User
import com.ems.moussafirdima.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {

    suspend operator fun invoke() {
        repository.deleteClient()
    }

}