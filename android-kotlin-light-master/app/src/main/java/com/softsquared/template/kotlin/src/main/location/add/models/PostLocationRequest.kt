package com.softsquared.template.kotlin.src.main.location.add.models

import com.google.gson.annotations.SerializedName

data class PostLocationRequest(
    @SerializedName("buildingName")
    val buildingName: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("addressDetail")
    val addressDetail: String,

    @SerializedName("addressGuide")
    val addressGuide: String,

    @SerializedName("addressLongitude")
    val addressLongitude: Double,

    @SerializedName("addressLatitude")
    val addressLatitude: Double,

    @SerializedName("addressTitle")
    val addressTitle: String,

    @SerializedName("addressType")
    val addressType : String
)
