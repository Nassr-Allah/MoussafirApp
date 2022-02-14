package com.ems.moussafirdima.ui.view_models.client_view_models

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.data.remote.dto.UserDto
import com.ems.moussafirdima.data.remote.dto.toUser
import com.ems.moussafirdima.domain.use_case.client.AddClientUseCase
import com.ems.moussafirdima.ui.view_models.states.AddClientState
import com.ems.moussafirdima.util.ImageUpload
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddClientViewModel @Inject constructor(
    private val addClientUseCase: AddClientUseCase
) : ViewModel() {

    private val _state = mutableStateOf(AddClientState())
    val state: State<AddClientState> = _state

    fun addClient(client: UserDto) {
        addClientUseCase(client).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AddClientState(client = result.data)
                }
                is Resource.Loading -> {
                    _state.value = AddClientState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = AddClientState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}