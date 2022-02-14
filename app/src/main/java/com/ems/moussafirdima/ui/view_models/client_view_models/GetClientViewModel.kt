package com.ems.moussafirdima.ui.view_models.client_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.data.remote.dto.toUser
import com.ems.moussafirdima.domain.use_case.client.GetClientUseCase
import com.ems.moussafirdima.ui.view_models.states.GetClientState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetClientViewModel @Inject constructor(
    private val getClientUseCase: GetClientUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GetClientState())
    val state: State<GetClientState> = _state

    fun getClient(token: String) {
        viewModelScope.launch {
            getClientUseCase(token).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = GetClientState(client = result.data?.user?.toUser())
                    }
                    is Resource.Loading -> {
                        _state.value = GetClientState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value =
                            GetClientState(error = result.message ?: "Unexpected Server Error")
                    }
                }
            }
        }
    }

}