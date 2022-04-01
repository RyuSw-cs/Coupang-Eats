package com.softsquared.template.kotlin.src.main.location.select.models

import com.google.gson.annotations.SerializedName

data class LocationSelectResponse(
    @SerializedName("userAddressIdx")
    val userAddressIdx: Int,

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