package com.ems.moussafirdima.ui.view_models.states

import com.ems.moussafirdima.domain.model.User
import retrofit2.Response

data class AddClientState(
    val isLoading: Boolean = false,
    val client: User? = null,
    val error: String = ""
)