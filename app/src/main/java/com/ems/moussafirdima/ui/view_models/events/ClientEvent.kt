package com.ems.moussafirdima.ui.view_models.events

import com.ems.moussafirdima.domain.model.User

sealed class ClientEvent {
    data class OnLoginClient(val client: User): ClientEvent()
    data class OnSaveClient(val client: User): ClientEvent()
    data class OnAddClient(val client: User): ClientEvent()
    data class OnInsertClient(val client: User): ClientEvent()
    data class OnDeleteClient(val client: User): ClientEvent()
}