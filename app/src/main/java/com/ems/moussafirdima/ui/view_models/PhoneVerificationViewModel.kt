package com.ems.moussafirdima.ui.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.verify_phone.PhoneVerificationUseCase
import com.ems.moussafirdima.ui.view_models.states.PhoneVerificationState
import com.ems.moussafirdima.util.Constants
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhoneVerificationViewModel @Inject constructor(
    private val phoneVerificationUseCase: PhoneVerificationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PhoneVerificationState())
    val state: State<PhoneVerificationState> = _state
    private val _savedStateHandel = savedStateHandle

    init {
        savedStateHandle.get<String>(Constants.PARAM_PHONE)?.let { phone ->
            verifyPhoneNumber(phone)
        }
    }

    private fun verifyPhoneNumber(phoneNumber: String) {
        phoneVerificationUseCase(phoneNumber).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    Log.d("PhoneVerification", result.data.toString())
                    _state.value = PhoneVerificationState(code = result.data!!.number)
                }
                is Resource.Loading -> {
                    Log.d("PhoneVerification", "Loading...")
                    _state.value = PhoneVerificationState(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d("PhoneVerification", result.message ?: "Unexpected Error")
                    _state.value = PhoneVerificationState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun recall() {
        _savedStateHandel.get<String>(Constants.PARAM_PHONE)?.let { phone ->
            verifyPhoneNumber(phone)
        }
    }

}