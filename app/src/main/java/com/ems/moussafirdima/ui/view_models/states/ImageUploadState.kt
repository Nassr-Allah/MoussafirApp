package com.ems.moussafirdima.ui.view_models.states

data class ImageUploadState(
    val isLoading: Boolean = false,
    val url: String = "",
    val error: String = ""
)
