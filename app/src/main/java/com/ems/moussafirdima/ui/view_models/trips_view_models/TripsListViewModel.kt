package com.ems.moussafirdima.ui.view_models.trips_view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.trips.GetTripsUseCase
import com.ems.moussafirdima.ui.view_models.states.TripsListState
import com.ems.moussafirdima.util.Constants
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TripsListViewModel @Inject constructor(
    private val getTripsUseCase: GetTripsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(TripsListState())
    val state: State<TripsListState> = _state

    init {
        getAllTrips()
    }

    private fun getAllTrips() {
        getTripsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = TripsListState(trips = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = TripsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = TripsListState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}