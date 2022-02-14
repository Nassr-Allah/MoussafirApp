package com.ems.moussafirdima.ui.view_models.stations_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.stations.GetStationsUseCase
import com.ems.moussafirdima.ui.view_models.states.StationsListState
import com.ems.moussafirdima.util.Constants
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationsListViewModel @Inject constructor(
    private val getStationsUseCase: GetStationsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(StationsListState())
    val state: State<StationsListState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_DESTINATION)?.let { destination ->
            getStations(destination)
        }
    }

    private fun getStations(destination: String) {
        getStationsUseCase(destination).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = StationsListState(stations = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = StationsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = StationsListState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}