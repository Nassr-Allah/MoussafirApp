package com.ems.moussafirdima.util

sealed class Event {
    object PopBackStack: Event()
    data class Navigate(val route: String): Event()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): Event()
}