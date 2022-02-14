package com.ems.moussafirdima.ui.view_models.client_view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.repository.ClientRepository
import com.ems.moussafirdima.domain.use_case.client.AutoLoginUseCase
import com.ems.moussafirdima.ui.view_models.states.ResponseState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AutoLoginViewModel @Inject constructor(
    private val autoLoginUseCase: AutoLoginUseCase,
    private val repository: ClientRepository
) : ViewModel() {

    private val _state = mutableStateOf(ResponseState())
    val state: State<ResponseState> = _state

    var clientToken = ""

    init {
        autoLogin()
    }

    private fun autoLogin() {
        autoLoginUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d("AutoLogin", "Loading")
                    _state.value = ResponseState(isLoading = true)
                }
                is Resource.Success -> {
                    Log.d("AutoLogin", result.data.toString())
                    _state.value = ResponseState(response = result.data)
                }
                is Resource.Error -> {
                    Log.d("AutoLogin", result.data.toString())
                    _state.value = ResponseState(error = result.message ?: "Unecpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}