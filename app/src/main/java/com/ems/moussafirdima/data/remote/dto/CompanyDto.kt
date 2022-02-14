package com.ems.moussafirdima.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyDto(
    @SerializedName("name")
    val name: String = ""
)