package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station(
    val name: String = "",
    val destinations: List<String> = mutableListOf(),
    val city: String = "",
    val lat: Float? = null,
    val lng: Float? = null,
    val distance: Int? = null
) : Parcelable
