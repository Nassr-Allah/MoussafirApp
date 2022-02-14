package com.ems.moussafirdima.ui.view_models.trips_view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.trips.GetTripsByDestinationUseCase
import com.ems.moussafirdima.ui.view_models.states.TripsListState
import com.ems.moussafirdima.util.Constants
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilteredTripsViewModel @Inject constructor(
    private val getTripsByDestinationUseCase: GetTripsByDestinationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TripsListState())
    val state: State<TripsListState> = _state

    init {
        val start = savedStateHandle.get<String>(Constants.PARAM_START) ?: ""
        Log.d("DetailsCard", "start is $start")
        savedStateHandle.get<String>(Constants.PARAM_DESTINATION)?.let { destination ->
            Log.d("DetailsCard", "Started call with $destination")
            getFilteredTrips(start, destination)
        }
    }

    private fun getFilteredTrips(start: String, destination: String) {
        getTripsByDestinationUseCase(start, destination).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = TripsListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = TripsListState(trips = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = TripsListState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}