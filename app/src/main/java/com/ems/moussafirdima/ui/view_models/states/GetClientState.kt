package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.domain.model.User

data class GetClientState(
    val isLoading: Boolean = false,
    val client: User? = null,
    val error: String = ""
)