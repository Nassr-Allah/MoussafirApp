package com.ems.moussafirdima.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddTicketDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("time")
    val time: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("trip")
    val trip: Int = 0,
    @SerializedName("client")
    val client: String = "",
)
