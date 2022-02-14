package com.ems.moussafirdima.ui.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.MapRoute
import com.ems.moussafirdima.domain.repository.RouteRepository
import com.ems.moussafirdima.domain.use_case.directions.GetDirectionsUseCase
import com.ems.moussafirdima.ui.view_models.states.DirectionState
import com.ems.moussafirdima.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DirectionsViewModel @Inject constructor(
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val repository: RouteRepository,
) : ViewModel() {

    private val _state = mutableStateOf(DirectionState())
    val state: State<DirectionState> = _state

    private val _mapRoute = mutableStateOf(MapRoute())
    val mapRoute: State<MapRoute> = _mapRoute

    init {
        getDirectionFromDb()
    }

    fun getDirection(origin: String, destination: String, key: String, date: String) {
        Log.d("DirectionViewModel", "get direction called")
        getDirectionsUseCase(origin, destination, key, date).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d("DirectionViewModel", result.toString())
                    _state.value = DirectionState(isLoading = true)
                }
                is Resource.Success -> {
                    Log.d("DirectionViewModel", result.toString())
                    _state.value = DirectionState(direction = result.data)
                }
                is Resource.Error -> {
                    Log.d("DirectionViewModel", result.toString())
                    _state.value = DirectionState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDirectionFromDb() {
        val date = "${getCurrentDay()}/${getCurrentMonth()}/${Calendar.getInstance().get(Calendar.YEAR)}"
        repository.getRoute().onEach { result ->
            Log.d("DirectionDb", result.toString())
            if (result != null && result.date < date) {
                repository.deleteRoute(result)
                getDirectionFromDb()
            } else {
                _mapRoute.value = result ?: MapRoute()
            }
        }.launchIn(viewModelScope)
    }

}