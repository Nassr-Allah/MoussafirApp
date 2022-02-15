package com.ems.moussafirdima.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MapRoute(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val encodedPath: String = "",
    val arrival: String = "",
    val duration: Long = 0,
    val date: String = "",
    val tripId: Int = 0
)
