package com.ems.moussafirdima.ui.view_models.client_view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.client.LoginClientUseCase
import com.ems.moussafirdima.ui.view_models.states.AddClientState
import com.ems.moussafirdima.ui.view_models.states.LoginClientState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginClientViewModel @Inject constructor(
    private val loginClientUseCase: LoginClientUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LoginClientState())
    val state: State<LoginClientState> = _state

    fun loginClient(username: String, password: String) {
        Log.d("LoginScreen", "started the view model call")
        loginClientUseCase(username, password).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = LoginClientState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = LoginClientState(token = result.data!!)
                }
                is Resource.Error -> {
                    _state.value = LoginClientState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}