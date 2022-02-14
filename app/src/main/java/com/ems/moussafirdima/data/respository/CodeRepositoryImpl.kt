package com.ems.moussafirdima.data.respository

import com.ems.moussafirdima.data.remote.MoussafirApi
import com.ems.moussafirdima.data.remote.dto.CodeDto
import com.ems.moussafirdima.domain.repository.CodeRepository
import javax.inject.Inject

class CodeRepositoryImpl  @Inject constructor(
    private val api: MoussafirApi
) : CodeRepository {

    override suspend fun verifyPhoneNumber(phoneNumber: String): CodeDto {
        return api.verifyPhoneNumber(phoneNumber)
    }

}