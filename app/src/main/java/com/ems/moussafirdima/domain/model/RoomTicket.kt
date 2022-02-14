package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class RoomTicket(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val date: String = "",
    val time: String = "",
    val numOfTickets: Int = 1,
    val status: String = "",
    val driver: String = "",
    val start: String = "",
    val destination: String = "",
    val price: Int = 0,
    val place: String = "",
    val passenger: String = "",
    val reservedBy: String = "",
    val picture: String = ""
) : Parcelable
