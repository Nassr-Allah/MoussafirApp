package com.ems.moussafirdima.ui.view_models.states

import retrofit2.Response

data class ResponseState(
    val isLoading: Boolean = false,
    val response: Response<Void>? = null,
    val error: String = ""
)