package com.ems.moussafirdima.data.remote.dto

import com.ems.moussafirdima.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    @SerializedName("profile_picture")
    val profilePicture: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("password1")
    val password: String = ""
)

fun UserDto.toUser(): User {
    return User(
        firstName,
        lastName,
        phoneNumber,
        profilePicture,
        address,
        gender,
        email
    )
}
