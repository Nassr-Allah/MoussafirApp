package com.ems.moussafirdima.ui.view_models.client_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.client.GetClientFromDbUseCase
import com.ems.moussafirdima.ui.view_models.states.GetClientState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetClientFromDbViewModel @Inject constructor(
    private val getClientFromDbUseCase: GetClientFromDbUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GetClientState())
    val state: State<GetClientState> = _state

    init {
        getClientFromDb()
    }

    private fun getClientFromDb() {
        getClientFromDbUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = GetClientState(client = result.data)
                }
                is Resource.Loading -> {
                    _state.value = GetClientState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = GetClientState(error = result.message ?: "Unexpected Database Error")
                }
            }

        }.launchIn(viewModelScope)
    }

}