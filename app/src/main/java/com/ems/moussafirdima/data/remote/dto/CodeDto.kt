package com.ems.moussafirdima.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CodeDto(
    @SerializedName("number")
    val number: String = ""
)