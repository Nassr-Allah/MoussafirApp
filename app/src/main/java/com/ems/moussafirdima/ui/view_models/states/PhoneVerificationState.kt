package com.ems.moussafirdima.ui.view_models.states

data class PhoneVerificationState(
    val isLoading: Boolean = false,
    val code: String = "",
    val error: String = ""
)