package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(
    val token: String = "",
    val user: User? = null
) : Parcelable
