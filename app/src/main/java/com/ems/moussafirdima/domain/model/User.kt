package com.ems.moussafirdima.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ems.moussafirdima.data.remote.dto.UserDto
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    var profilePicture: String = "",
    val address: String = "",
    var gender: String = "",
    val email: String = "",
    var password: String = "",
    @PrimaryKey(autoGenerate = false)
    var token: String = "",
): Parcelable

fun User.toUserDto(): UserDto {
    return UserDto(
        firstName,
        lastName,
        phoneNumber,
        profilePicture,
        address,
        gender,
        email,
        password
    )
}
