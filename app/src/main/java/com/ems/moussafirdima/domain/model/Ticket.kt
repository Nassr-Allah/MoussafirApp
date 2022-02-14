package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
    val id: Int = 0,
    val date: String = "",
    val time: String = "",
    val numOfTickets: Int = 1,
    var status: String = "",
    val trip: Trip? = null,
    val client: Client? = null,
    val reservedBy: String = "",
    val price: Int = 0,
    val placeNumber: String = "",
    val number: String = ""
) : Parcelable