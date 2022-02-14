package com.ems.moussafirdima.domain.repository

import com.ems.moussafirdima.data.remote.dto.CodeDto

interface CodeRepository {

    suspend fun verifyPhoneNumber(phoneNumber: String): CodeDto

}