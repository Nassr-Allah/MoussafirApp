package com.ems.moussafirdima.ui.view_models.states

import android.location.Location

data class LocationState(
    val isLoading: Boolean = false,
    val location: Location? = null,
    val error: String = ""
)