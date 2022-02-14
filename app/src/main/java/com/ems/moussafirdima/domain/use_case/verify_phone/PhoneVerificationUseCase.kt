package com.ems.moussafirdima.domain.use_case.verify_phone

import com.ems.moussafirdima.data.remote.dto.CodeDto
import com.ems.moussafirdima.domain.repository.CodeRepository
import com.ems.moussafirdima.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PhoneVerificationUseCase @Inject constructor(
    private val repository: CodeRepository
) {

    operator fun invoke(phoneNumber: String) : Flow<Resource<CodeDto>> = flow {
        try {
            emit(Resource.Loading<CodeDto>())
            val code = repository.verifyPhoneNumber(phoneNumber)
            emit(Resource.Success<CodeDto>(data = code))
        } catch (e: HttpException) {
            emit(Resource.Error<CodeDto>(message = e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<CodeDto>(message = e.localizedMessage ?: "Unexpected Error"))
        }
    }

}