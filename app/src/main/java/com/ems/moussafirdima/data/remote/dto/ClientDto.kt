package com.ems.moussafirdima.data.remote.dto

import com.ems.moussafirdima.domain.model.Client
import com.google.gson.annotations.SerializedName

data class ClientDto(
    @SerializedName("token")
    val token: String = "",
    @SerializedName("user")
    val user: UserDto? = null
)

fun ClientDto.toClient(): Client {
    return Client(token = token, user = user?.toUser())
}
