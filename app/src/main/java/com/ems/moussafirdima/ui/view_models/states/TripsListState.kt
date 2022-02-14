package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.domain.model.Trip

data class TripsListState(
    val isLoading: Boolean = false,
    val trips: List<Trip> = emptyList(),
    val error: String = ""
)
