package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trip(
    val id: Int = 0,
    val start: String = "",
    val destination: String = "",
    val price: Int = 0,
    val status: String = "",
    val time: String = "",
    val numOfPlaces: Int = 0,
    val driver: String = ""
) : Parcelable

