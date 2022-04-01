package com.softsquared.template.kotlin.src.main.join.models

import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : String
)