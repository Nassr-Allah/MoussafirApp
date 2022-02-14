package com.ems.moussafirdima.ui.view_models.ticket_view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.use_case.tickets.PostTicketUseCase
import com.ems.moussafirdima.ui.view_models.states.ResponseState
import com.ems.moussafirdima.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketPostViewModel @Inject constructor(
    private val postTicketUseCase: PostTicketUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ResponseState())
    val state: State<ResponseState> = _state

    fun postTicket(date: String, time: String, numOfTickets: Int, tripId: Int) {
        postTicketUseCase(date, time, numOfTickets, tripId).onEach { result ->
            Log.d("AddTicketFlow", result.toString())
            when(result) {
                is Resource.Loading -> {
                    _state.value = ResponseState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ResponseState(response = result.data)
                }
                is Resource.Error -> {
                    _state.value = ResponseState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}