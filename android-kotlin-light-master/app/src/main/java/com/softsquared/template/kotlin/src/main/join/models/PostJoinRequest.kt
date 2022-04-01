package com.softsquared.template.kotlin.src.main.join.models

import com.google.gson.annotations.SerializedName

data class PostJoinRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String
)