package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.data.remote.dto.directions_api.Direction

data class DirectionState(
    val isLoading: Boolean = false,
    val direction: Direction? = null,
    val error: String = ""
)