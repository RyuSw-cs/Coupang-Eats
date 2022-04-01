package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models

import com.google.gson.annotations.SerializedName

data class PostLocationSelectRequest(
    @SerializedName("address")
    val address: String,

    @SerializedName("addressDetail")
    val addressDetail: String,

    @SerializedName("addressGuide")
    val addressGuide: String,

    @SerializedName("userLongitude")
    val userLongitude: Double,

    @SerializedName("userLatitude")
    val userLatitude: Double,

    @SerializedName("addressType")
    val addressType: String,

    @SerializedName("status")
    val status: String
)
