package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.domain.model.Station

data class StationsListState(
    val isLoading: Boolean = false,
    val stations: List<Station> = emptyList(),
    val error: String = ""
)
