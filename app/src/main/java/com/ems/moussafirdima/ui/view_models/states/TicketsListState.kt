package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.domain.model.RoomTicket
import com.ems.moussafirdima.domain.model.Ticket

data class TicketsListState(
    val isLoading: Boolean = false,
    val tickets: List<Ticket> = emptyList(),
    val error: String = ""
)
