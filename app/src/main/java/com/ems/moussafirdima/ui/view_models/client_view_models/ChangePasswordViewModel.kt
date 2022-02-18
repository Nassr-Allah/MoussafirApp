package com.ems.moussafirdima.ui.view_models.client_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.client.ChangePasswordUseCase
import com.ems.moussafirdima.ui.view_models.states.ResponseState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ResponseState())
    val state: State<ResponseState> = _state

    fun changePassword(phoneNumber: String, password: String) {
        changePasswordUseCase(phoneNumber, password).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = ResponseState(response = result.data)
                }
                is Resource.Loading -> {
                    _state.value = ResponseState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ResponseState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}