package com.ems.moussafirdima.ui.view_models.states

data class LoginClientState(
    val isLoading: Boolean = false,
    val token: String = "",
    val error: String = ""
)