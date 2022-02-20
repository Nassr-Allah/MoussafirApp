package com.ems.moussafirdima.ui.view_models.stations_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.Station
import com.ems.moussafirdima.domain.use_case.stations.GetAllStationsUseCase
import com.ems.moussafirdima.ui.view_models.states.StationsListState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AllStationsViewModel @Inject constructor(
    private val getAllStationsUseCase: GetAllStationsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StationsListState())
    val state: State<StationsListState> = _state

    private var fixedStations = listOf<Station>()

    private val _stations = mutableStateOf(listOf<Station>())
    val filteredStations: State<List<Station>> = _stations

    init {
        getAllStations()
    }

    private fun getAllStations() {
        getAllStationsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = StationsListState(stations = result.data ?: emptyList())
                    _stations.value = result.data ?: emptyList()
                    fixedStations = result.data ?: emptyList()
                }
                is Resource.Loading -> {
                    _state.value = StationsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = StationsListState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterStation(query: String) {
        val stations = mutableListOf<Station>()
        for (station in fixedStations) {
            if (station.name.contains(query, true)) {
                stations.add(station)
            }
        }
        _stations.value = stations
    }

}