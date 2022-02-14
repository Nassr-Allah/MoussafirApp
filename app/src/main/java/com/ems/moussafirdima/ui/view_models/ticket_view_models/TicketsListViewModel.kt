package com.ems.moussafirdima.ui.view_models.ticket_view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.model.Ticket
import com.ems.moussafirdima.domain.repository.TicketsRepository
import com.ems.moussafirdima.domain.use_case.tickets.GetClientTicketsUseCase
import com.ems.moussafirdima.ui.screens.main_app.trip.ticketsList
import com.ems.moussafirdima.ui.view_models.states.TicketsListState
import com.ems.moussafirdima.util.Constants
import com.ems.moussafirdima.util.Resource
import com.ems.moussafirdima.util.getCurrentDay
import com.ems.moussafirdima.util.getCurrentMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    private val getClientTicketsUseCase: GetClientTicketsUseCase,
    private val repository: TicketsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(TicketsListState())
    val state: State<TicketsListState> = _state

    private var fixedTickets = listOf<Ticket>()

    init {
        savedStateHandle.get<String>(Constants.PARAM_TOKEN)?.let { token ->
            getTickets(token)
        }
    }

    private fun getTickets(token: String) {
        getClientTicketsUseCase(token).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = TicketsListState(tickets = result.data ?: emptyList())
                    fixedTickets = _state.value.tickets
                }
                is Resource.Loading -> {
                    _state.value = TicketsListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = TicketsListState(error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun applyFilter(filter: String) {
        val calendar = GregorianCalendar.getInstance()
        val today = "${getCurrentDay()}/${getCurrentMonth()}/${calendar.get(Calendar.YEAR)}"
        val lastWeek = "${calendar.get(Calendar.DAY_OF_MONTH) - 7}/${getCurrentMonth()}/${calendar.get(Calendar.YEAR)}"
        val lastMonth = "${getCurrentDay()}/${calendar.get(Calendar.MONTH) - 1}/${calendar.get(Calendar.YEAR)}"

        when(filter) {
            "Paid" -> {
                val tickets = mutableListOf<Ticket>()
                for (ticket in fixedTickets) {
                    if (ticket.status == "paid") {
                        tickets.add(ticket)
                    }
                }
                _state.value = TicketsListState(tickets = tickets)
            }
            "Canceled" -> {
                val tickets = mutableListOf<Ticket>()
                for (ticket in fixedTickets) {
                    if (ticket.status == "canceled") {
                        tickets.add(ticket)
                    }
                }
                _state.value = TicketsListState(tickets = tickets)
            }
            "Today" -> {
                val tickets = mutableListOf<Ticket>()
                for (ticket in fixedTickets) {
                    Log.d("TicketsScreen", "$today == ${ticket.date}")
                    if (ticket.date == today) {
                        tickets.add(ticket)
                    }
                }
                _state.value = TicketsListState(tickets = tickets)
            }
            "Last week" -> {
                val tickets = mutableListOf<Ticket>()
                for (ticket in fixedTickets) {
                    if (ticket.date > lastWeek) {
                        tickets.add(ticket)
                    }
                }
                _state.value = TicketsListState(tickets = tickets)
            }
            "Last month" -> {
                val tickets = mutableListOf<Ticket>()
                for (ticket in fixedTickets) {
                    if (ticket.date > lastMonth) {
                        tickets.add(ticket)
                    }
                }
                _state.value = TicketsListState(tickets = tickets)
            }
            else -> _state.value = TicketsListState(tickets = fixedTickets)
        }
    }

}