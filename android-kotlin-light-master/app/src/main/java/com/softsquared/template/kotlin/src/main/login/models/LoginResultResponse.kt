package com.softsquared.template.kotlin.src.main.login.models

import com.google.gson.annotations.SerializedName

data class LoginResultResponse(
    @SerializedName("userIdx")
    val userIdx: String,

    @SerializedName("jwt")
    val jwt: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)